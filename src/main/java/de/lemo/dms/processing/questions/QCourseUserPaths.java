/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QCourseUserPaths.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/processing/questions/QCourseUserPaths.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Stopwatch;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.UserPathLink;

/**
 * Computes paths for users
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("courseuserpaths")
public class QCourseUserPaths extends Question {

	@SuppressWarnings("unchecked")
	@POST
	public JSONObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) Long endTime,
			@FormParam(MetaParam.GENDER) List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) List<Long> learningObjects
			) throws JSONException {

		validateTimestamps(startTime, endTime);

		final Stopwatch stopWatch = new Stopwatch();
		stopWatch.start();

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria;
		List<Long> users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());

		criteria = session.createCriteria(ILog.class, "log")
				.add(Restrictions.in("log.course.id", courses))
				.add(Restrictions.between("log.timestamp", startTime, endTime));
		if (!users.isEmpty()) {
				criteria.add(Restrictions.in("log.user.id", users));
		}
		else
		{
			this.logger.debug("No users found for courses. Returning empty JSONObject.");
			return new JSONObject();
		}
		if(!learningObjects.isEmpty())
		{
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}

		final List<ILog> logs = criteria.list();

		this.logger.debug("Found " + users.size() + " actions. " + +stopWatch.elapsedTime(TimeUnit.SECONDS));

		long courseCount = 0;
		final BiMap<Course, Long> courseNodePositions = HashBiMap.create();
		final Map<Long/* user id */, List<Long/* course id */>> userPaths = Maps.newHashMap();

		this.logger.debug("Paths fetched: " + logs.size() + ". " + stopWatch.elapsedTime(TimeUnit.SECONDS));
		
		Map<Long, Long> idToAlias = StudentHelper.getCourseStudentsRealKeys(courses, gender); 

		for (final ILog log : logs) 
		{
			
			final Course course = log.getCourse();
			Long nodeID = courseNodePositions.get(course);
			if (nodeID == null) {
				nodeID = courseCount++;
				courseNodePositions.put(course, nodeID);
			}

			final long userId = idToAlias.get(log.getUser().getId());

			List<Long> nodeIDs = userPaths.get(userId);
			if (nodeIDs == null) {
				nodeIDs = Lists.newArrayList();
				userPaths.put(userId, nodeIDs);
			}
			nodeIDs.add(nodeID);
		}

		this.logger.debug("userPaths: " + userPaths.size());

		final Map<Long /* node id */, List<UserPathLink>> coursePaths = Maps.newHashMap();

		for (final Entry<Long, List<Long>> userEntry : userPaths.entrySet()) {

			UserPathLink lastLink = null;

			for (final Long nodeID : userEntry.getValue()) {
				List<UserPathLink> links = coursePaths.get(nodeID);
				if (links == null) {
					links = Lists.newArrayList();
					coursePaths.put(nodeID, links);
				}
				final UserPathLink link = new UserPathLink(String.valueOf(nodeID), "0");
				links.add(link);

				if (lastLink != null) {
					lastLink.setTarget(String.valueOf(nodeID));
				}
				lastLink = link;
			}
		}
		stopWatch.stop();
		this.logger.debug("coursePaths: " + coursePaths.size());
		this.logger.debug("Total Fetched log entries: " + (logs.size() + logs.size()) + " log entries."
				+ stopWatch.elapsedTime(TimeUnit.SECONDS));

		final Set<UserPathLink> links = Sets.newHashSet();

		final JSONObject result = new JSONObject();
		final JSONArray nodes = new JSONArray();
		final JSONArray edges = new JSONArray();

		for (final Entry<Long, List<UserPathLink>> courseEntry : coursePaths.entrySet()) {
			final JSONObject node = new JSONObject();
			node.put("name", courseNodePositions.inverse().get(courseEntry.getKey()).getTitle());
			node.put("value", courseEntry.getValue().size());
			node.put("group", courses.contains(courseNodePositions.inverse().get(courseEntry.getKey())) ? 1 : 2);
			nodes.put(node);

			for (final UserPathLink edge : courseEntry.getValue()) {
				if (edge.getTarget() == edge.getSource()) {
					continue;
				}
				links.add(edge);
			}
		}

		for (final UserPathLink link : links) {
			final JSONObject edgeJSON = new JSONObject();
			edgeJSON.put("target", link.getTarget());
			edgeJSON.put("source", link.getSource());
			edges.put(edgeJSON);
		}

		this.logger.debug("Nodes: " + nodes.length() + ", Links: " + edges.length() + "   / time: "
				+ stopWatch.elapsedTime(TimeUnit.SECONDS));

		result.put("nodes", nodes);
		result.put("links", edges);
		session.close();
		return result;
	}
}
