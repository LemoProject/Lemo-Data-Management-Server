/**
 * File ./main/java/de/lemo/dms/service/ServiceUserInformation.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

/**
 * Service for user information. Provide all courses for a user
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceUserInformation {

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Returns courseObjects for all courses of the specified user.
	 * 
	 * @param id	User identifier
	 * @param count	Number of courses that shall be returned (for users with lots of courses)
	 * @param offset	
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/{uid}/courses")
	public ResultListCourseObject getCoursesByUser(@PathParam("uid") final long id,
			@QueryParam("course_count") final Long count,
			@QueryParam("course_offset") final Long offset) {
		this.logger.info("## " + id);
		List<CourseObject> courses = new ArrayList<CourseObject>();

		// Set up db-connection
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.eq("cu.user.id", id));
		List<CourseUserMining> couUseMin = criteria.list();

		final ArrayList<CourseMining> courseList = new ArrayList<CourseMining>();
		for(CourseUserMining courseUser : couUseMin)
		{
			courseList.add(courseUser.getCourse());
		}

		for (CourseMining course : courseList) 
		{
			List<Long> l1 = new ArrayList<Long>();
			l1.add(id);
			final List<Long> participants = StudentHelper.getCourseStudents(l1);
			criteria = session.createCriteria(ILogMining.class, "log");
			criteria.add(Restrictions.eq("log.course.id", course.getId()));
			if(participants.size() > 0)
			{
				criteria.add(Restrictions.in("log.user.id", participants));
			}
			List<ILogMining> logs = criteria.list();
			Collections.sort(logs);
			Long lastTime = 0L;
			Long firstTime = 0L;
			
			if(logs.size() > 0)
			{
				lastTime = logs.get(logs.size()-1).getTimestamp();
				firstTime = logs.get(0).getTimestamp();
			}

			final CourseObject courseObject = new CourseObject(course.getId(), course.getShortname(), course
					.getTitle(),
					participants.size(), lastTime, firstTime);
			courses.add(courseObject);
		}
		dbHandler.closeSession(session);
		
		if(count != null && count > 0)
		{
			if(offset != null && offset > 0 )
			{
				if(courses.size() - offset >= count)
				{
					courses = courses.subList(offset.intValue(), offset.intValue() + count.intValue());
				}
				else
				{
					courses = courses.subList(offset.intValue(), courses.size()-1);
				}
			}
			else
			{
				if(courses.size() > count)
				{
					courses = courses.subList(0, count.intValue());
				}
			}
			
		}
		
		return new ResultListCourseObject(courses);
	}

}
