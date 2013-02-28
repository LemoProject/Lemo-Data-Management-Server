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
import java.util.Map.Entry;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
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
	 * @param courseIds
	 *            List of course-identifiers
	 * @param userIds
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
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courseIds,
			@FormParam(MetaParam.USER_IDS) List<Long> userIds,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		validateTimestamps(startTime, endTime);

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria;
		if(userIds == null || userIds.size() == 0)
		{
			userIds = StudentHelper.getCourseStudents(courseIds);
		}

		// Create criteria for log-file-search
		criteria = session.createCriteria(ILogMining.class, "log")
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.in("log.user.id", userIds));

		if (!courseIds.isEmpty()) {
			criteria.add(Restrictions.in("log.course.id", courseIds));
		}
		if (!userIds.isEmpty()) {
			criteria.add(Restrictions.in("log.user.id", userIds));
		}

		@SuppressWarnings("unchecked")
		final List<ILogMining> list = criteria.list();

		this.logger.info("Total matched entries: " + list.size());

		final HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();

		for (final ILogMining ilo : list)
		{
			// TODO use Class.getSimpleName() instead?
			final String obType = ilo
					.getClass()
					.toString()
					.substring(ilo.getClass().toString().lastIndexOf(".") + 1,
							ilo.getClass().toString().lastIndexOf("Log"));

			if ((types == null) || (types.size() == 0) || types.contains(obType.toUpperCase()))
			{
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
		final Long id = 0L;
		for (final Entry<String, ArrayList<Long>> item : requests.entrySet())
		{
			final String title = item.getKey().substring(item.getKey().indexOf("$") + 1);
			final String type = item.getKey().substring(item.getKey().indexOf("?") + 1, item.getKey().indexOf("$"));
			final ResourceRequestInfo rri = new ResourceRequestInfo(id,
					ELearningObjectType.valueOf(type.toUpperCase()), Long.valueOf(item.getValue().size()),
					Long.valueOf(new HashSet<Long>(item.getValue()).size()), title, 0L);
			result.add(rri);
		}
		this.logger.info("Total returned entries: " + result.getResourceRequestInfos().size());

		return result;
	}

}
