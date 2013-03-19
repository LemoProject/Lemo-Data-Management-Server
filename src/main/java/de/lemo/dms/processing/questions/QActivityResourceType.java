/**
 * File ./main/java/de/lemo/dms/processing/questions/QActivityResourceType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;

/**
 * Checks which resources are used in certain courses
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("activityresourcetype")
public class QActivityResourceType extends Question {

	@SuppressWarnings("unchecked")
	@POST
	public ResultListResourceRequestInfo compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.TYPES) List<String> resourceTypes) {

		validateTimestamps(startTime, endTime);

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
		boolean allTypes = resourceTypes.isEmpty();
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		
		List<Long> users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses).values());
		if(users.isEmpty()) {
			return new ResultListResourceRequestInfo();
		}
		
		if(resourceTypes != null && !resourceTypes.isEmpty())
		{
			List<String> tmp = new ArrayList<String>();
			for(String s : resourceTypes)
			{
				tmp.add(s.toUpperCase());
			}
			resourceTypes = tmp;
		}
		
		final Criteria criteria = session.createCriteria(ILogMining.class, "log");
		criteria.add(Restrictions.in("log.course.id", courses))
				.add(Restrictions.between("log.timestamp", startTime, endTime));
				criteria.add(Restrictions.in("log.user.id", users));

		final List<ILogMining> logs = criteria.list();
		
		
		final Map<Long, ResourceRequestInfo> rriMap = new HashMap<Long, ResourceRequestInfo>();
		final Map<Long, Set<Long>> userMap = new HashMap<Long, Set<Long>>();
		for(ILogMining log : logs)
		{
			String type = log.getClass().getSimpleName().toUpperCase();
			if(type.contains("LOG"))
			{
				type = type.substring(0, type.indexOf("LOG"));
			}
			if (log.getLearnObjId() != null && log.getUser() != null && (resourceTypes.contains(type) || allTypes))
			{
				Long id = Long.valueOf(log.getPrefix() + "" + log.getLearnObjId());
				if (rriMap.get(id) == null) {
					Set<Long> userSet = new HashSet<Long>();
					userSet.add(log.getUser().getId());
					userMap.put(id, userSet);
					rriMap.put(id, new ResourceRequestInfo(id, ELearningObjectType.valueOf(type), 1L, 1L, log.getTitle(), 0L));
				} else
				{
					userMap.get(id).add(log.getUser().getId());
					rriMap.get(id).incRequests();
					rriMap.get(id).setUsers(((Integer)userMap.get(id).size()).longValue());
				}
			}
		}
		if (rriMap.values() != null) {
			result.addAll(rriMap.values());
		}
		return result;
	}

}
