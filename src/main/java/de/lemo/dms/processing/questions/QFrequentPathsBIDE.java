/**
 * File ./main/java/de/lemo/dms/processing/questions/QFrequentPathsBIDE.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ca.pfv.spmf.sequentialpatterns.AlgoBIDEPlus;
import ca.pfv.spmf.sequentialpatterns.SequenceDatabase;
import ca.pfv.spmf.sequentialpatterns.Sequences;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

@Path("frequentPaths")
public class QFrequentPathsBIDE extends Question {

	private static HashMap<String, ILogMining> idToLogM = new HashMap<String, ILogMining>();
	private static HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();
	private static HashMap<String, Integer> idToInternalId = new HashMap<String, Integer>();
	private static HashMap<Integer, String> internalIdToId = new HashMap<Integer, String>();

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
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		final ArrayList<UserPathNode> nodes = Lists.newArrayList();
		final ArrayList<UserPathLink> links = Lists.newArrayList();

		if ((courses != null) && (courses.size() > 0))
		{
			System.out.print("Parameter list: Courses: " + courses.get(0));
			for (int i = 1; i < courses.size(); i++) {
				System.out.print(", " + courses.get(i));
			}
			System.out.println();
		}
		if ((users != null) && (users.size() > 0))
		{
			System.out.print("Parameter list: Users: " + users.get(0));
			for (int i = 1; i < users.size(); i++) {
				System.out.print(", " + users.get(i));
			}
			System.out.println();
		}
		if ((types != null) && (types.size() > 0))
		{
			System.out.print("Parameter list: Types: : " + types.get(0));
			for (int i = 1; i < types.size(); i++) {
				System.out.print(", " + types.get(i));
			}
			System.out.println();
		}
		if ((minLength != null) && (maxLength != null) && (minLength < maxLength))
		{
			System.out.println("Parameter list: Minimum path length: : " + minLength);
			System.out.println("Parameter list: Maximum path length: : " + maxLength);
		}
		System.out.println("Parameter list: Minimum Support: : " + minSup);
		System.out.println("Parameter list: Session Wise: : " + sessionWise);
		System.out.println("Parameter list: Start time: : " + startTime);
		System.out.println("Parameter list: End time: : " + endTime);

		try {

			// Session session = dbHandler.getMiningSession();

			/**
			 * FileWriter out = new FileWriter("./"+System.currentTimeMillis()+"_BIDEresults.txt"); PrintWriter pout =
			 * new
			 * PrintWriter(out);
			 * //Write header for the output file pout.println("# LeMo - Sequential pattern data");
			 **/

			final SequenceDatabase sequenceDatabase = new SequenceDatabase();

			if (!sessionWise) {
				sequenceDatabase.loadLinkedList(QFrequentPathsBIDE.generateLinkedList(courses, users, types, minLength,
						maxLength,
						startTime, endTime));
				// sequenceDatabase.loadFile(generateInputFile(courseIds, userIds, startTime, endTime));
			} else {
				// sequenceDatabase.loadFile(generateInputFileSessionBound(courseIds, userIds, startTime, endTime));
				sequenceDatabase.loadLinkedList(QFrequentPathsBIDE.generateLinkedListSessionBound(courses, users,
						types, minLength,
						maxLength, startTime, endTime));
			}

			final AlgoBIDEPlus algo = new AlgoBIDEPlus(minSup);

			// execute the algorithm
			final Clock c = new Clock();
			final Sequences res = algo.runAlgorithm(sequenceDatabase);
			System.out.println("Time for BIDE-calculation: " + c.get());

			final LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();
			Long pathId = 0L;
			System.out.println();
			for (int i = 0; i < res.getLevelCount(); i++)
			{
				for (int j = 0; j < res.getLevel(i).size(); j++)
				{
					String predecessor = null;
					final Long absSup = Long.valueOf(res.getLevel(i).get(j).getAbsoluteSupport());
					pathId++;
					System.out.println("New " + i + "-Sequence. Support : "
							+ res.getLevel(i).get(j).getAbsoluteSupport());
					for (int k = 0; k < res.getLevel(i).get(j).size(); k++)
					{

						final String obId = QFrequentPathsBIDE.internalIdToId.get(res.getLevel(i).get(j).get(k)
								.getItems().get(0).getId());

						final ILogMining ilo = QFrequentPathsBIDE.idToLogM.get(obId);

						final String type = ilo.getClass().getSimpleName();

						final String posId = String.valueOf(pathObjects.size());

						if (predecessor != null)
						{
							pathObjects.put(
									posId,
									new UserPathObject(posId, ilo.getTitle(), absSup, type,
											Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId,
											Long.valueOf(QFrequentPathsBIDE.requests.get(obId).size()), Long
													.valueOf(new HashSet<Long>(
															QFrequentPathsBIDE.requests.get(obId)).size())));

							// Increment or create predecessor edge
							pathObjects.get(predecessor).addEdgeOrIncrement(posId);
						}
						else
						{
							pathObjects.put(
									posId,
									new UserPathObject(posId, ilo.getTitle(), absSup,
											type, Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId, Long
													.valueOf(QFrequentPathsBIDE.requests.get(obId).size()), Long
													.valueOf(new HashSet<Long>(
															QFrequentPathsBIDE.requests.get(obId)).size())));
						}
						predecessor = posId;
					}

				}
			}
			System.out.println("\n");

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
			e.printStackTrace();
		} finally
		{
			QFrequentPathsBIDE.requests.clear();
			QFrequentPathsBIDE.idToLogM.clear();
			QFrequentPathsBIDE.internalIdToId.clear();
			QFrequentPathsBIDE.idToInternalId.clear();
		}
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
	private static LinkedList<String> generateLinkedListSessionBound(final List<Long> courses, final List<Long> users,
			final List<String> types, final Long minLength, final Long maxLength, final Long starttime,
			final Long endtime)
	{
		final LinkedList<String> result = new LinkedList<String>();
		try {
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

			final Session session = dbHandler.getMiningSession();

			final Criteria criteria = session.createCriteria(ILogMining.class, "log");
			if (courses.size() > 0) {
				criteria.add(Restrictions.in("log.course.id", courses));
			}
			if (users.size() > 0) {
				criteria.add(Restrictions.in("log.user.id", users));
			}
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			final ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

			System.out.println("Read " + list.size() + " logs.");
			final ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();

			final HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
			int max = 0;
			for (int i = 0; i < list.size(); i++)
			{
				if ((list.get(i).getUser() != null) && (list.get(i).getLearnObjId() != null)) {
					if (logMap.get(list.get(i).getUser().getId()) == null)
					{
						final ArrayList<ILogMining> a = new ArrayList<ILogMining>();
						a.add(list.get(i));
						logMap.put(list.get(i).getUser().getId(), a);
						if (logMap.get(list.get(i).getUser().getId()).size() > max) {
							max = logMap.get(list.get(i).getUser().getId()).size();
						}
					}
					else
					{

						logMap.get(list.get(i).getUser().getId()).add(list.get(i));
						Collections.sort(logMap.get(list.get(i).getUser().getId()));
						if (logMap.get(list.get(i).getUser().getId()).size() > max) {
							max = logMap.get(list.get(i).getUser().getId()).size();
						}
						if (list.get(i).getDuration() == -1)
						{
							uhis.add(new ArrayList<ILogMining>(logMap.get(list.get(i).getUser().getId())));
							logMap.remove(list.get(i).getUser().getId());
						}
					}
				}
			}

			uhis.addAll(logMap.values());

			final Integer[] lengths = new Integer[(max / 10) + 1];
			for (int i = 0; i < lengths.length; i++) {
				lengths[i] = 0;
			}

			for (int i = 0; i < uhis.size(); i++)
			{
				lengths[uhis.get(i).size() / 10]++;
			}

			for (int i = 0; i < lengths.length; i++) {
				if (lengths[i] != 0)
				{
					System.out.println("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
				}
			}

			System.out.println("Generated " + uhis.size() + " user histories. Max length @ " + max);

			int z = 0;

			// Write data to output file
			for (final Iterator<ArrayList<ILogMining>> iter = uhis.iterator(); iter.hasNext();)
			{
				final ArrayList<ILogMining> l = iter.next();
				if (l.size() > 5)
				{
					String line = "";
					for (int i = 0; i < l.size(); i++)
					{
						if (QFrequentPathsBIDE.idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null) {
							QFrequentPathsBIDE.idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(),
									l.get(i));
						}

						if (QFrequentPathsBIDE.requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
						{
							final ArrayList<Long> us = new ArrayList<Long>();
							us.add(l.get(i).getUser().getId());
							QFrequentPathsBIDE.requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
						} else {
							QFrequentPathsBIDE.requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(
									l.get(i).getUser().getId());
						}

						line += l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ";
					}
					line += "-2";
					result.add(line);
					z++;
				}

			}
			System.out.println("Wrote " + z + " user histories.");

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return result;
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
	private static LinkedList<String> generateLinkedList(final List<Long> courses, final List<Long> users,
			final List<String> types,
			final Long minLength, final Long maxLength, final Long starttime, final Long endtime)
	{
		final LinkedList<String> result = new LinkedList<String>();
		final boolean hasBorders = (minLength != null) && (maxLength != null) && (maxLength > 0)
				&& (minLength < maxLength);
		final boolean hasTypes = (types != null) && (types.size() > 0);
		try {
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

			final Session session = dbHandler.getMiningSession();

			final Criteria criteria = session.createCriteria(ILogMining.class, "log");
			if (courses.size() > 0) {
				criteria.add(Restrictions.in("log.course.id", courses));
			}
			if (users.size() > 0) {
				criteria.add(Restrictions.in("log.user.id", users));
			}
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			final ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

			System.out.println("Read " + list.size() + " logs.");

			int max = 0;

			final HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();

			// int pre = 1000;

			for (int i = 0; i < list.size(); i++)
			{
				if ((list.get(i).getUser() != null) && (list.get(i).getLearnObjId() != null)) {
					// If there isn't a user history for this user-id create a new one
					if (logMap.get(list.get(i).getUser().getId()) == null)
					{
						// User histories are saved in an ArrayList of ILogMining-objects
						final ArrayList<ILogMining> a = new ArrayList<ILogMining>();
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
			final ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();// (logMap.values());
			int id = 1;
			for (final ArrayList<ILogMining> uLog : logMap.values())
			{

				final ArrayList<ILogMining> tmp = new ArrayList<ILogMining>();
				boolean containsType = false;
				for (final ILogMining iLog : uLog)
				{
					if (QFrequentPathsBIDE.idToInternalId.get(iLog.getPrefix() + " " + iLog.getLearnObjId()) == null)
					{
						QFrequentPathsBIDE.internalIdToId.put(id, iLog.getPrefix() + " " + iLog.getLearnObjId());
						QFrequentPathsBIDE.idToInternalId.put(iLog.getPrefix() + " " + iLog.getLearnObjId(), id);
						id++;
					}
					if (hasTypes) {
						for (final String type : types)
						{
							if (iLog.getClass().getSimpleName().toLowerCase().contains(type.toLowerCase()))
							{
								containsType = true;
								tmp.add(iLog);
								break;
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
					System.out.println("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
				}
			}

			System.out.println("Generated " + uhis.size() + " user histories. Max length @ " + max);

			int z = 0;
			// Convert all user histories or "paths" into the format, that is requested by the BIDE-algorithm-class
			for (final ArrayList<ILogMining> l : uhis)
			{
				String line = "";
				for (int i = 0; i < l.size(); i++)
				{
					if (QFrequentPathsBIDE.idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null) {
						QFrequentPathsBIDE.idToLogM
								.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));
					}

					// Update request numbers
					if (QFrequentPathsBIDE.requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
					{
						final ArrayList<Long> us = new ArrayList<Long>();
						us.add(l.get(i).getUser().getId());
						QFrequentPathsBIDE.requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
					} else {
						QFrequentPathsBIDE.requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(
								l.get(i).getUser().getId());
					}
					// The id of the object gets the prefix, indicating it's class. This is important for distinction
					// between objects of different ILogMining-classes but same ids
					line += QFrequentPathsBIDE.idToInternalId
							.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) + " -1 ";
				}
				line += "-2";
				System.out.println(line);
				result.add(line);
				z++;
			}
			System.out.println("Wrote " + z + " logs.");
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
