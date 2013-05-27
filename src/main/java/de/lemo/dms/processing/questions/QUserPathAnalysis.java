/**
 * File ./main/java/de/lemo/dms/processing/questions/QUserPathAnalysis.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

/**
 * Generates a list of Nodes and edges, representing the user-navigation
 * 
 * @author Sebastian Schwarzrock
 */
@Path("userpathanalysis")
public class QUserPathAnalysis extends Question {

	/**
	 * Returns a list of Nodes and edges, representing the user-navigation
	 * matching the requirements given by the parameters.
	 * 
	 * @see ELearningObjectType
	 * @param courses
	 *            List of course-identifiers
	 * @param users
	 *            List of user-identifiers
	 * @param types
	 *            List of learn object types (see ELearnObjType)
	 * @param considerLogouts
	 *            If user-paths should be cut when a logout appears this must be
	 *            set to "true".
	 * @param startTime
	 *            LongInteger time stamp
	 * @param endTime
	 *            LongInteger time stamp
	 * @return
	 */
	@POST
	public ResultListUserPathGraph compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.LOGOUT_FLAG) final Boolean considerLogouts,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender) {

		validateTimestamps(startTime, endTime);

		// DB-initialization
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

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
		
		// Create criteria for log-file-search
		criteria = session.createCriteria(ILogMining.class, "log");

		criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
		criteria.addOrder(Order.asc("log.timestamp"));

		if (!courses.isEmpty()) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}

		if (!users.isEmpty()) {
			criteria.add(Restrictions.in("log.user.id", users));
		}

		@SuppressWarnings("unchecked")
		final List<ILogMining> list = criteria.list();

		Collections.sort(list);

		this.logger.debug("Total matched entries: " + list.size());

		// Map for UserPathObjects
		final LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();

		// Map for user histories
		final HashMap<Long, List<ILogMining>> userHis = Maps.newHashMap();

		int skippedUsers = 0;
		// Generate user histories
		for (final ILogMining log : list) {
			if (log.getUser() == null) {
				skippedUsers++;
				continue;
			}
			boolean typeOk = true;
			if (!types.isEmpty()) {
				typeOk = false;
				for (final String type : types) {
					/*
					 * XXX why is this checked for every log object and not a
					 * criteria restriction?
					 */
					// Check if ILog-object has acceptable learningObjectType
					if (ELearningObjectType.fromLogMiningType(log).toString().equals(type))
					{
						typeOk = true;
						break;
					}
				}
			}

			if (typeOk) {
				if (userHis.get(log.getUser().getId()) == null)
				{
					// If user is new create a new entry in the hash map and add
					// log item
					userHis.put(log.getUser().getId(), new ArrayList<ILogMining>());
					userHis.get(log.getUser().getId()).add(log);
				} else {
					userHis.get(log.getUser().getId()).add(log);
				}
			}
		}

		this.logger.debug("Skipped entries with missing user id: " + skippedUsers);

		int skippedLogs = 0;
		// Generate paths from user histories
		for (final List<ILogMining> l : userHis.values()) {
			String predNode = null;
			for (int i = 0; i < l.size(); i++) {
				if ((l.get(i) != null) && (l.get(i).getUser() != null))
				{
					final ILogMining current = l.get(i);

					final Long learnObjId = current.getLearnObjId();
					if (learnObjId == null) {
						skippedLogs++;
						continue;
					}
					final String learnObjType = ELearningObjectType.fromLogMiningType(current).toString();
					final String type = current
							.getClass()
							.toString()
							.substring(current.getClass().toString().lastIndexOf(".") + 1,
									current.getClass().toString().lastIndexOf("Log"));
					final String cId = learnObjId + "-" + learnObjType;
					// Determines whether it's a new path (no predecessor for
					// current node) or not

					UserPathObject knownPath;
					if (predNode != null)
					{
						String cIdPos = null;
						if ((knownPath = pathObjects.get(cId)) == null)
						{
							// If the node is new create entry in hash map
							cIdPos = String.valueOf(pathObjects.size());
							pathObjects.put(cId, new UserPathObject(cIdPos, current.getTitle(), 1L, type,
									Double.valueOf(current.getDuration()), 1L, 0L, 0L, 0L));
						}
						else
						{
							// If the node is already known, increase weight
							pathObjects.get(cId).increaseWeight(Double.valueOf(current.getDuration()));
							cIdPos = knownPath.getId();
						}

						// Increment or create predecessor edge
						pathObjects.get(predNode).addEdgeOrIncrement(cIdPos);
					}
					else if (pathObjects.get(cId) == null)
					{
						final String cIdPos = String.valueOf(pathObjects.size());
						pathObjects.put(cId, new UserPathObject(cIdPos, current.getTitle(), 1L,
								type, Double.valueOf(current.getDuration()), 1L, 0L, 0L, 0L));
					} else {
						pathObjects.get(cId).increaseWeight(Double.valueOf(current.getDuration()));
					}

					if (considerLogouts && (current.getDuration() == -1L)) {
						predNode = null;
					} else {
						predNode = cId;
					}
				}
			}
		}
		this.logger.debug("Skipped entries with missing learn object id: " + skippedLogs);

		final ArrayList<UserPathNode> nodes = Lists.newArrayList();
		final ArrayList<UserPathLink> links = Lists.newArrayList();

		for (final UserPathObject pathEntry : pathObjects.values()) {

			final UserPathObject path = pathEntry;
			path.setPathId(pathEntry.getPathId());
			nodes.add(new UserPathNode(path));
			final String sourcePos = path.getId();

			for (final Entry<String, Integer> linkEntry : pathEntry.getEdges().entrySet())
			{
				final UserPathLink link = new UserPathLink();
				link.setSource(sourcePos);
				link.setPathId(path.getPathId());
				link.setTarget(linkEntry.getKey());
				link.setValue(String.valueOf(linkEntry.getValue()));
				if (link.getSource() != link.getTarget()) {
					links.add(link);
				}
			}
		}
		session.close();
		return new ResultListUserPathGraph(nodes, links);
	}

}
