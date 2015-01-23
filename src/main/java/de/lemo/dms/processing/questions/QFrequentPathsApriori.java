/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QFrequentPathsApriori.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./main/java/de/lemo/dms/processing/questions/QFrequentPathsApriori.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.FrequentPath;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

/**
 * Reads the path data from the database and using a algorithm to find frequent paths
 * 
 * @author Sebastian Schwarzrock
 */
@Path("frequentPathsApriori")
public class QFrequentPathsApriori extends Question {

	private Map<Integer, ILog> idToLogM = new HashMap<Integer, ILog>();
	private Map<Integer, List<Long>> requests = new HashMap<Integer, List<Long>>();
	private Map<Integer, Integer> idToInternalId = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> internalIdToId = new HashMap<Integer, Integer>();
	private ArrayList<Integer> objInd = new ArrayList<Integer>();
	private int userCount = 0;
	@POST
	public ResultListUserPathGraph compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) final List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.MIN_LENGTH) final Long minLength,
			@FormParam(MetaParam.MAX_LENGTH) final Long maxLength,
			@FormParam(MetaParam.MIN_SUP) final Double minSup,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) final List<Long> learningObjects) {

		validateTimestamps(startTime, endTime);
		final ArrayList<UserPathNode> nodes = Lists.newArrayList();
		final ArrayList<UserPathLink> links = Lists.newArrayList();
		logger.info(startTime + " " + endTime);

		if (logger.isDebugEnabled()) {
			if ((courses != null) && (courses.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Courses: " + courses.get(0));
				for (int i = 1; i < courses.size(); i++) {
					buffer.append(", " + courses.get(i));
				}
				logger.debug(buffer.toString());
			}
			if ((users != null) && (users.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Users: " + users.get(0));
				for (int i = 1; i < users.size(); i++) {
					buffer.append(", " + users.get(i));
				}
				logger.debug(buffer.toString());
			}
			if ((types != null) && (types.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Types: : " + types.get(0));
				for (int i = 1; i < types.size(); i++) {
					buffer.append(", " + types.get(i));
				}
				logger.debug(buffer.toString());
			}
			if ((minLength != null) && (maxLength != null) && (minLength < maxLength))
			{
				logger.debug("Parameter list: Minimum path length: : " + minLength);
				logger.debug("Parameter list: Maximum path length: : " + maxLength);
			}
			logger.debug("Parameter list: Minimum Support: : " + minSup);
			logger.debug("Parameter list: Start time: : " + startTime);
			logger.debug("Parameter list: End time: : " + endTime);
		}
		

		
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		final Session session = dbHandler.getMiningSession();
		List<List<Integer>> list = generateList(courses, users, types, minLength, maxLength, startTime, endTime,
				session, gender, learningObjects);

		List<List<List<Integer>>> patterns = FrequentPath.apriori(list, minSup.doubleValue());
		final LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();
		Long pathId = 0L;
		
		for(int i = patterns.size()-1; i >=0 ; i-- )
		{
			logger.info("Found " + patterns.get(i).size() + " paths of length " + patterns.get(i).get(0).size());
			for(int j = 0; j < patterns.get(i).size(); j++)
			{
				
				String predecessor = null;
				Long absSup = (long) (minSup * userCount) + 1;
				pathId++;
				for(int k = 0; k < patterns.get(i).get(j).size(); k++)
				{
					final String posId = String.valueOf(pathObjects.size());
					int obj = patterns.get(i).get(j).get(k);
					final ILog ilo = this.idToLogM.get(objInd.get(obj));
					
					if (predecessor != null)
					{
						pathObjects.put(
								posId,
								new UserPathObject(posId, ilo.getLearning().getTitle(), absSup, ilo.getClass().getSimpleName(),
										0d, ilo.getPrefix(), pathId,
										Long.valueOf(this.requests.get(obj).size()), Long
												.valueOf(new HashSet<Long>(this.requests.get(obj))
														.size())));

						// Increment or create predecessor edge
						pathObjects.get(predecessor).addEdgeOrIncrement(posId);
					}
					else
					{
						pathObjects.put(
								posId,
								new UserPathObject(posId, ilo.getLearning().getTitle(), absSup,
										ilo.getClass().getSimpleName(), 0d, ilo.getPrefix(), pathId, Long
												.valueOf(this.requests.get(obj).size()), Long
												.valueOf(new HashSet<Long>(this.requests.get(obj))
														.size())));
					}
					predecessor = posId;
				}
			}
		}
		for (final UserPathObject pathEntry : pathObjects.values()) {

			final UserPathObject path = pathEntry;
			path.setWeight(path.getWeight());
			path.setPathId(pathEntry.getPathId());
			nodes.add(new UserPathNode(path, true));
			final String sourcePos = path.getId();

			for (final Entry<String, Integer> linkEntry : pathEntry.getEdges().entrySet()) {
				final UserPathLink link = new UserPathLink();
				link.setSource(sourcePos);
				link.setPathId(path.getPathId());
				link.setTarget(linkEntry.getKey());
				link.setValue(String.valueOf(linkEntry.getValue()));
				links.add(link);
			}
		}

		this.requests.clear();
		this.idToLogM.clear();
		this.internalIdToId.clear();
		this.idToInternalId.clear();

		logger.info("Found " + pathId + " paths.");
		session.close();
		return new ResultListUserPathGraph(nodes, links);
	}
	
	/**
	 * Generates the necessary list containing the sequences (user paths) for the algorithm
	 * 
	 * @param courses
	 *            Course-Ids
	 * @param users
	 *            User-Ids
	 * @param starttime
	 *            Start time
	 * @param endtime
	 *            End time
	 * @return The path to the generated file
	 */
	@SuppressWarnings("unchecked")
	public List<List<Integer>> generateList(final List<Long> courses, List<Long> users,
			final List<String> types, final Long minLength, final Long maxLength, final Long starttime,
			final Long endtime, Session session, List<Long> gender, List<Long> learningObjects){
		
		final List<List<Integer>> result = new ArrayList<List<Integer>>();
		final boolean hasBorders = (minLength != null) && (maxLength != null) && (maxLength > 0)
				&& (minLength < maxLength);
		final boolean hasTypes = (types != null) && (types.size() > 0);
		Map<Long, ILearningObject> objects = new HashMap<Long, ILearningObject>();
		
		Criteria criteria;
		if(users == null || users.size() == 0)
		{
			users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
		}
		else
		{
			Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}

		criteria = session.createCriteria(ILog.class, "log");
		if (courses.size() > 0) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if (users.size() > 0) {
			criteria.add(Restrictions.in("log.user.id", users));
		}
		if(!learningObjects.isEmpty())
		{
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}
		criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
		criteria.addOrder(Property.forName("log.timestamp").asc());
		
		final ArrayList<ILog> list = (ArrayList<ILog>) criteria.list();
		Map<Long, List<Integer>> userPaths = new HashMap<Long, List<Integer>>();
		
		logger.debug("Found " + list.size() + " logs.");
		long id = -1;
		List<Integer> path = new ArrayList<Integer>();
		for(ILog log : list)
		{
			Integer pos = ((Long)log.getLearning().getId()).intValue();

			if(!hasTypes || types.contains(log.getLearning().getLOType()))
			{
				if(!objInd.contains(pos))
				{
					objInd.add(pos);
				}
				if(idToLogM.get(pos) == null )
				{
					idToLogM.put(pos, log);
				}
				if (this.requests.get(objInd.indexOf(pos)) == null)
				{
					final ArrayList<Long> us = new ArrayList<Long>();
					us.add(log.getUser().getId());
					this.requests.put(objInd.indexOf(pos), us);
				} else {
					this.requests.get(objInd.indexOf(pos)).add(
							log.getUser().getId());
				}				
				
				if(userPaths.get(log.getUser().getId()) == null)
				{					
					List<Integer> l = new ArrayList<Integer>();
					l.add(objInd.indexOf(pos));
					userPaths.put(log.getUser().getId(), l);				
				}
				else
				{
					userPaths.get(log.getUser().getId()).add(objInd.indexOf(pos));
				}
			}
			/*if(!hasTypes || types.contains(log.getLearning().getLOType()))
			{
				if(objects.get(log.getLearning()) == null)
					objects.put(log.getLearning().getId(), log.getLearning());
				if(id != log.getUser().getId())
				{
					id = log.getUser().getId();
					if(!hasBorders || (path.size() < maxLength && path.size() > minLength))
					{
						List<Integer> userPath = new ArrayList<Integer>();
						for(int i = 0; i < path.size(); i++)
						{
							userPath.add(path.get(i).intValue());	
						}
						if(userPath.size() > 0)
							result.add(userPath);
					}
					path = new ArrayList<Integer>();
				}
				if(!objInd.contains(pos))
				{
					objInd.add(pos);
				}
				if(idToLogM.get(pos) == null )
				{
					idToLogM.put(pos, log);
				}
				if (this.requests.get(objInd.indexOf(pos)) == null)
				{
					final ArrayList<Long> us = new ArrayList<Long>();
					us.add(log.getUser().getId());
					this.requests.put(objInd.indexOf(pos), us);
				} else {
					this.requests.get(objInd.indexOf(pos)).add(
							log.getUser().getId());
				}
				path.add(objInd.indexOf(pos));
			}
					*/
		}	

		for(List<Integer> uhis : userPaths.values())
		{
			result.add(uhis);
		}
		
		userCount = result.size();
		logger.debug("Found " + result.size() + " user paths.");
		return result;
	}
}
