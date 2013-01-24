/**
 * File ./main/java/de/lemo/dms/processing/questions/QCourseUserPaths.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import java.util.Date;
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
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.UserPathLink;

@Path("courseuserpaths")
public class QCourseUserPaths extends Question {

	@POST
	public JSONObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courseIds,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) Long endTime) throws JSONException {

		/*
		 * This is the first usage of Criteria API in the project and therefore a bit more documented than usual, to
		 * serve as example implementation for other analyses.
		 */

		if (endTime == null) {
			endTime = new Date().getTime();
		}

		if ((startTime >= endTime) || (courseIds == null) || courseIds.isEmpty()) {
			return null;
		}

		final Stopwatch stopWatch = new Stopwatch();

		stopWatch.start();

		/* A criteria is created from the session. */
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		/*
		 * HQL-like equivalent: "Select from ILogMining". ILogMining is an interface, so Hibernate will load all classes
		 * implementing it. The string argument is an alias.
		 */
		final Criteria criteria = session.createCriteria(ILogMining.class, "log");

		/*
		 * Restrictions equivalent to HQL where:
		 * 
		 * where course in ( ... ) and timestamp between " + startTime + " AND " + endTime;
		 */
		criteria.add(Restrictions.in("log.course.id", courseIds))
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.eq("log.action", "view"));

		/* Calling list() eventually performs the query */
		@SuppressWarnings("unchecked")
		final List<ILogMining> logs = criteria.list();

		final Set<Long/* user id */> users = Sets.newHashSet();
		for (final ILogMining log : logs) {
			final UserMining user = log.getUser();
			if (user == null) {
				continue;
			}
			users.add(user.getId());
		}

		this.logger.info("Found " + users.size() + " actions. " + +stopWatch.elapsedTime(TimeUnit.SECONDS));

		if (users.isEmpty()) {
			return new JSONObject();
		}

		final Criteria exdendedCriteria = session.createCriteria(ILogMining.class, "log");
		exdendedCriteria.add(Restrictions.in("log.user.id", users))
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.eq("log.action", "view"));
		@SuppressWarnings("unchecked")
		final List<ILogMining> extendedLogs = exdendedCriteria.list();

		long courseCount = 0;
		final BiMap<CourseMining, Long> courseNodePositions = HashBiMap.create();
		final Map<Long/* user id */, List<Long/* course id */>> userPaths = Maps.newHashMap();

		this.logger.info("Paths fetched: " + extendedLogs.size() + ". " + stopWatch.elapsedTime(TimeUnit.SECONDS));

		for (final ILogMining log : extendedLogs) {
			final UserMining user = log.getUser();
			if ((user == null) || (log.getCourse() == null)) {
				continue;
			}

			final CourseMining course = log.getCourse();
			Long nodeID = courseNodePositions.get(course);
			if (nodeID == null) {
				nodeID = courseCount++;
				courseNodePositions.put(course, nodeID);
			}

			final long userId = log.getUser().getId();

			List<Long> nodeIDs = userPaths.get(userId);
			if (nodeIDs == null) {
				nodeIDs = Lists.newArrayList();
				userPaths.put(userId, nodeIDs);
			}
			nodeIDs.add(nodeID);
		}

		this.logger.info("userPaths: " + userPaths.size());

		final Map<Long /* node id */, List<UserPathLink>> coursePaths = Maps.newHashMap();

		for (final Entry<Long, List<Long>> userEntry : userPaths.entrySet()) {

			UserPathLink lastLink = null;
			// Long userID = userEntry.getKey();

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
		this.logger.info("coursePaths: " + coursePaths.size());
		this.logger.info("Total Fetched log entries: " + (logs.size() + extendedLogs.size()) + " log entries."
				+ stopWatch.elapsedTime(TimeUnit.SECONDS));

		final Set<UserPathLink> links = Sets.newHashSet();

		final JSONObject result = new JSONObject();
		final JSONArray nodes = new JSONArray();
		final JSONArray edges = new JSONArray();

		for (final Entry<Long, List<UserPathLink>> courseEntry : coursePaths.entrySet()) {
			final JSONObject node = new JSONObject();
			node.put("name", courseNodePositions.inverse().get(courseEntry.getKey()).getTitle());
			// node.put("name", "");
			node.put("value", courseEntry.getValue().size());
			node.put("group", courseIds.contains(courseNodePositions.inverse().get(courseEntry.getKey())) ? 1 : 2);
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

		this.logger.info("Nodes: " + nodes.length() + ", Links: " + edges.length() + "   / time: "
				+ stopWatch.elapsedTime(TimeUnit.SECONDS));

		result.put("nodes", nodes);
		result.put("links", edges);
		return result;
	}
}
