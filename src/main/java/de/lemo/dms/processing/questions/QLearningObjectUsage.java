/**
 * File ./main/java/de/lemo/dms/processing/questions/QLearningObjectUsage.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseLORelation;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;

/**
 * Use of learning objects
 * 
 * @author Sebastian Schwarzrock
 */
@Path("learningobjectusage")
public class QLearningObjectUsage extends Question {

	/**
	 * Returns a list of resources and their respective statistics of usage.
	 * 
	 * @see ELearningObjectType
	 * @param courses
	 *            List of course-identifiers
	 * @param users
	 *            List of user-identifiers
	 * @param types
	 *            List of learn object types (see ELearnObjType)
	 * @param startTime
	 *            LongInteger time stamp
	 * @param endTime
	 *            LongInteger time stamp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListResourceRequestInfo compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		validateTimestamps(startTime, endTime);
		if (users.isEmpty()) {
			users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses).values());
		}
		else
		{
			Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses);
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}
		

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		// Create criteria for log-file-search
		Criteria criteria = session.createCriteria(ILogMining.class, "log")
				.add(Restrictions.between("log.timestamp", startTime, endTime));
		if (!courses.isEmpty()) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if (!users.isEmpty()) {
			criteria.add(Restrictions.in("log.user.id", users));
		}

		final List<ILogMining> logs = criteria.list();

		this.logger.info("Total matched entries: " + logs.size());

		final HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();
		HashSet<String> requestedObjects = new HashSet<String>();
		
		for (final ILogMining ilo : logs)
		{
			// TODO use Class.getSimpleName() instead?
			final String obType = ilo
					.getClass()
					.toString()
					.substring(ilo.getClass().toString().lastIndexOf(".") + 1,
							ilo.getClass().toString().lastIndexOf("Log"));

			if ((types == null) || (types.size() == 0) || types.contains(obType.toUpperCase()))
			{
				requestedObjects.add(ilo.getPrefix() + " " + ilo.getLearnObjId());
				final String id = ilo.getPrefix() + "_" + ilo.getLearnObjId() + "?" + obType + "$" + ilo.getTitle();
				if (requests.get(id) == null)
				{
					final ArrayList<Long> al = new ArrayList<Long>();
					al.add(ilo.getUser().getId());
					requests.put(id, al);
				} else {
					requests.get(id).add(ilo.getUser().getId());
				}
			}
		}
		
		//Adding RRIs for unused Objects
		criteria = session.createCriteria(ICourseLORelation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));
		List<ICourseLORelation> asoList = criteria.list();
		
		
		Long id = 1L;
		
		for(ICourseLORelation aso : asoList)
		{
			String obId = aso.getLearningObject().getPrefix() + " " + aso.getLearningObject().getId();
			if(!requestedObjects.contains(obId))
			{
				String type = aso.getLearningObject().getClass().getSimpleName().toUpperCase();
				if(type.contains("MINING"))
				{
					type = type.substring(0, type.indexOf("MINING"));
				}
				if(types.contains(type))
				{			
					final ResourceRequestInfo rri = new ResourceRequestInfo(id,
							ELearningObjectType.valueOf(type), 0L, 0L,
							aso.getLearningObject().getTitle(), 0L);
					result.add(rri);
					id++;
				}
			}
		}
		
		

		for (final Entry<String, ArrayList<Long>> item : requests.entrySet())
		{
			final String title = item.getKey().substring(item.getKey().indexOf("$") + 1);
			final String type = item.getKey().substring(item.getKey().indexOf("?") + 1, item.getKey().indexOf("$"));
			final ResourceRequestInfo rri = new ResourceRequestInfo(id,
					ELearningObjectType.valueOf(type.toUpperCase()), Long.valueOf(item.getValue().size()),
					Long.valueOf(new HashSet<Long>(item.getValue()).size()), title, 0L);
			id++;
			result.add(rri);
		}
		this.logger.info("Total returned entries: " + result.getResourceRequestInfos().size());
		session.close();
		return result;
	}
}
