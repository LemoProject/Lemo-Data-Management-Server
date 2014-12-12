/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QFrequentPathsViger.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QFrequentPathsViger.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ca.pfv.spmf.sequentialpatterns.AlgoFournierViger08;
import ca.pfv.spmf.sequentialpatterns.SequenceDatabase;
import ca.pfv.spmf.sequentialpatterns.Sequences;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

/**
 * Read ther path data from the database and using the Bide algorithm to generates the frequent paths
 * without interruptions
 * 
 * @author Sebastian Schwarzrock
 */
@Path("frequentPathsViger")
public class QFrequentPathsViger extends Question {

	private Map<String, ILog> idToLogM = new HashMap<String, ILog>();
	private Map<String, List<Long>> requests = new HashMap<String, List<Long>>();
	private Map<String, Integer> idToInternalId = new HashMap<String, Integer>();
	private Map<Integer, String> internalIdToId = new HashMap<Integer, String>();

	@POST
	public ResultListUserPathGraph compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) final List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.MIN_LENGTH) final Long minLength,
			@FormParam(MetaParam.MAX_LENGTH) final Long maxLength,
			@FormParam(MetaParam.MIN_SUP) final Double minSup,
			@FormParam(MetaParam.SESSION_WISE) final boolean sessionWise,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) final List<Long> learningObjects) {

		validateTimestamps(startTime, endTime);
		
		final ArrayList<UserPathNode> nodes = Lists.newArrayList();
		final ArrayList<UserPathLink> links = Lists.newArrayList();

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
			logger.debug("Parameter list: Session Wise: : " + sessionWise);
			logger.debug("Parameter list: Start time: : " + startTime);
			logger.debug("Parameter list: End time: : " + endTime);
		}
		
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		final Session session = dbHandler.getMiningSession();

		try
		{
			final SequenceDatabase sequenceDatabase = new SequenceDatabase();

			sequenceDatabase.loadLinkedList(generateLinkedList(courses, users, types, minLength,
					maxLength, startTime, endTime, session, gender, learningObjects));

			final AlgoFournierViger08 algo = new AlgoFournierViger08(minSup, 0L, 1L, 0L, 1000L, null, true, false);

			// execute the algorithm
			final Clock c = new Clock();
			final Sequences res = algo.runAlgorithm(sequenceDatabase);
			logger.debug("Time for Hirate-calculation: " + c.get());

			final LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();
			Long pathId = 0L;
			for (int i = res.getLevelCount()-1; i >= 0 ; i--)
			{
				for (int j = 0; j < res.getLevel(i).size(); j++)
				{
					String predecessor = null;
					final Long absSup = Long.valueOf(res.getLevel(i).get(j).getAbsoluteSupport());
					pathId++;
					logger.debug("New " + i + "-Sequence. Support : "
							+ res.getLevel(i).get(j).getAbsoluteSupport());
					for (int k = 0; k < res.getLevel(i).get(j).size(); k++)
					{

						Integer id =res.getLevel(i).get(j).get(k).getItems().get(0).getId().intValue();
						
						final String obId = this.internalIdToId.get(id);

						final ILog ilo = this.idToLogM.get(obId);

						final String type = ilo.getClass().getSimpleName();

						final String posId = String.valueOf(pathObjects.size());

						if (predecessor != null)
						{
							pathObjects.put(
									posId,
									new UserPathObject(posId, ilo.getLearning().getTitle(), absSup, type,
											0d, ilo.getPrefix(), pathId,
											Long.valueOf(this.requests.get(obId).size()), Long
													.valueOf(new HashSet<Long>(this.requests.get(obId))
															.size())));

							// Increment or create predecessor edge
							pathObjects.get(predecessor).addEdgeOrIncrement(posId);
						}
						else
						{
							pathObjects.put(
									posId,
									new UserPathObject(posId, ilo.getLearning().getTitle(), absSup,
											type, 0d, ilo.getPrefix(), pathId, Long
													.valueOf(this.requests.get(obId).size()), Long
													.valueOf(new HashSet<Long>(this.requests.get(obId))
															.size())));
						}
						predecessor = posId;
					}

				}
			}
			logger.debug("\n");

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

		} catch (final Exception e)
		{
			logger.error(e.getMessage());
			
		} finally
		{
			this.requests.clear();
			this.idToLogM.clear();
			this.internalIdToId.clear();
			this.idToInternalId.clear();
		}
		session.close();
		return new ResultListUserPathGraph(nodes, links);
	}

	/**
	 * Generates the necessary list of input-strings, containing the sequences (user paths) for the BIDE+ algorithm
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
	private LinkedList<String> generateLinkedList(final List<Long> courses, List<Long> users,
			final List<String> types, final Long minLength, final Long maxLength, final Long starttime,
			final Long endtime, Session session, List<Long> gender, List<Long> learningObjects)
	{
		final LinkedList<String> result = new LinkedList<String>();
		final boolean hasBorders = (minLength != null) && (maxLength != null) && (maxLength > 0)
				&& (minLength < maxLength);
		final boolean hasTypes = (types != null) && (types.size() > 0);


		
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
		final ArrayList<ILog> list = (ArrayList<ILog>) criteria.list();

		logger.debug("Read " + list.size() + " logs.");

		int max = 0;

		final HashMap<Long, ArrayList<ILog>> logMap = new HashMap<Long, ArrayList<ILog>>();

		for (int i = 0; i < list.size(); i++)
		{
			if ((list.get(i).getUser() != null) && (list.get(i).getLearningId() != null)) {
				// If there isn't a user history for this user-id create a new one
				if (logMap.get(list.get(i).getUser().getId()) == null)
				{
					// User histories are saved in an ArrayList of ILogMining-objects
					final ArrayList<ILog> a = new ArrayList<ILog>();
					// Add current ILogMining-object to user-history
					a.add(list.get(i));
					// Add user history to the user history map
					logMap.put(list.get(i).getUser().getId(), a);
				}
				else
				{
					// Add current ILogMining-object to user-history
					logMap.get(list.get(i).getUser().getId()).add(list.get(i));
					// Sort the user's history (by time stamp)
					Collections.sort(logMap.get(list.get(i).getUser().getId()));
				}
			}
		}

		// Just changing the container for the user histories
		final ArrayList<ArrayList<ILog>> uhis = new ArrayList<ArrayList<ILog>>();
		int id = 1;
		for (final ArrayList<ILog> uLog : logMap.values())
		{

			final ArrayList<ILog> tmp = new ArrayList<ILog>();
			boolean containsType = false;
			for (final ILog iLog : uLog)
			{
				if (this.idToInternalId.get(iLog.getPrefix() + " " + iLog.getLearningId()) == null)
				{
					this.internalIdToId.put(id, iLog.getPrefix() + " " + iLog.getLearningId());
					this.idToInternalId.put(iLog.getPrefix() + " " + iLog.getLearningId(), id);
					id++;
				}
				if (hasTypes) {
					if (types.contains(iLog.getLearning().getLOType()))
					{
						{
							containsType = true;
							tmp.add(iLog);
						}

					}
				}
				if (!hasTypes) {
					tmp.add(iLog);
				}
			}
			if ((!hasBorders || ((tmp.size() >= minLength) && (tmp.size() <= maxLength)))
					&& (!hasTypes || containsType))
			{
				uhis.add(tmp);
				if (tmp.size() > max) {
					max = tmp.size();
				}
			}
		}

		// This part is only for statistics - group histories of similar length together and display there
		// respective lengths
		final Integer[] lengths = new Integer[(max / 10) + 1];
		for (int i = 0; i < lengths.length; i++) {
			lengths[i] = 0;
		}

		for (int i = 0; i < uhis.size(); i++) {
			lengths[uhis.get(i).size() / 10]++;
		}

		for (int i = 0; i < lengths.length; i++) {
			if (lengths[i] != 0)
			{
				logger.debug("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
			}
		}

		logger.debug("Generated " + uhis.size() + " user histories. Max length @ " + max);

		int z = 0;

		// Convert all user histories or "paths" into the format, that is requested by the BIDE-algorithm-class
		for (final ArrayList<ILog> l : uhis)
		{
			String line = "";
			for (int i = 0; i < l.size(); i++)
			{
				if (this.idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearningId()) == null) {
					this.idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearningId(),
							l.get(i));
				}

				// Update request numbers
				if (this.requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearningId()) == null)
				{
					final ArrayList<Long> us = new ArrayList<Long>();
					us.add(l.get(i).getUser().getId());
					this.requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearningId(), us);
				} else {
					this.requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearningId()).add(
							l.get(i).getUser().getId());
				}
				// The id of the object gets the prefix, indicating it's class. This is important for distinction
				// between objects of different ILogMining-classes but same ids
				line += "<" + i + "> " + this.idToInternalId.get(l.get(i).getPrefix() + " "
						+ l.get(i).getLearningId()) + " -1 ";
			}
			line += "-2";
			result.add(line);
			z++;
		}
		logger.debug("Wrote " + z + " logs.");
		return result;
	}

}
