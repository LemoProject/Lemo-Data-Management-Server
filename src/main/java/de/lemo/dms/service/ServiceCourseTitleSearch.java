/**
 * File ./main/java/de/lemo/dms/service/ServiceCourseTitleSearch.java
 * Date 2013-03-15
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

/**
 * Service to get details for all courses having titles matching the searched string
 * 
 * @author Sebastian Schwarzrock
 */
@Path("coursesbytext")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceCourseTitleSearch {

	/**
	 * Gets the details of all courses whose title matches search text. Informations include id, title, description, 
	 * number of participants, time of first student-request, time of latest student-request.
	 * 
	 * @param text	.
	 * 
	 * @return	A List of CourseObjects containing the information.
	 */
	@GET
	public ResultListCourseObject getCoursesByText(@QueryParam(MetaParam.SEARCH_TEXT) final String text,
			@QueryParam(MetaParam.RESULT_AMOUNT) final Long count,
			@QueryParam(MetaParam.OFFSET) final Long offset ) {

		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		List<CourseObject> result = new ArrayList<CourseObject>();
		result.add(new CourseObject());
		result.add(new CourseObject());

		if (text == null || text.equals("")) {
			return new ResultListCourseObject(result);
		}

		// Set up db-connection
		final Session session = dbHandler.getMiningSession();
		

		Criteria criteria = session.createCriteria(CourseMining.class, "course");
		criteria.add(Restrictions.ilike("course.title", text, MatchMode.ANYWHERE));

		@SuppressWarnings("unchecked")
		final ArrayList<CourseMining> courses = (ArrayList<CourseMining>) criteria.list();
		List<Long> ids = new ArrayList<Long>();
		
		for(CourseMining course : courses)
		{
			ids.add(course.getId());
		}
		
		Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(ids, new ArrayList<Long>());

		for (CourseMining courseMining : courses) {
			
			criteria = session.createCriteria(ILogMining.class, "log");
			criteria.add(Restrictions.eq("log.course.id", courseMining.getId()));
			if(userMap.size() > 0)
			{
				criteria.add(Restrictions.in("log.user.id", userMap.values()));
			}

			@SuppressWarnings("unchecked")
			ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
			Collections.sort(logs);

			Long lastTime = 0L;
			Long firstTime = 0L;

			if (logs.size() > 0)
			{
				lastTime = logs.get(logs.size() - 1).getTimestamp();
				firstTime = logs.get(0).getTimestamp();
			}
			final CourseObject co = new CourseObject(courseMining.getId(), courseMining.getShortname(),
					courseMining.getTitle(), userMap.size(), lastTime, firstTime);
			result.add(co);
		}
		
		if(count != null && count > 0)
		{
			if(offset != null && offset > 0 )
			{
				if(result.size() - offset >= count)
				{
					result = result.subList(offset.intValue(), offset.intValue() + count.intValue());
				}
				else
				{
					result = result.subList(offset.intValue(), courses.size()-1);
				}
			}
			else
			{
				if(result.size() > count)
				{
					result = result.subList(0, count.intValue());
				}
			}
			
		}
		session.close();
		return new ResultListCourseObject(result);
	}

}
