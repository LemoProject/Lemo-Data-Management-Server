/**
 * File ./src/main/java/de/lemo/dms/service/ServiceUserInformation.java
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
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.abstractions.ILog;
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
		List<CourseObject> courses = new ArrayList<CourseObject>();

		// Set up db-connection
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria = session.createCriteria(CourseUser.class, "cu");
		criteria.add(Restrictions.eq("cu.user.id", id));
		List<CourseUser> couUseMin = criteria.list();

		final ArrayList<Course> courseList = new ArrayList<Course>();
		for(CourseUser courseUser : couUseMin)
		{
			courseList.add(courseUser.getCourse());
		}
		
		List<Long> l1 = new ArrayList<Long>();
		l1.add(id);
		
		final List<Long> participants = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(l1, new ArrayList<Long>()).values());

		for (Course course : courseList) 
		{
			criteria = session.createCriteria(ILog.class, "log");
			criteria.add(Restrictions.eq("log.course.id", course.getId()));
			if(participants.size() > 0)
			{
				criteria.add(Restrictions.in("log.user.id", participants));
			}
			List<ILog> logs = criteria.list();
			Collections.sort(logs);
			Long lastTime = 0L;
			Long firstTime = 0L;
			
			if(logs.size() > 0)
			{
				lastTime = logs.get(logs.size()-1).getTimestamp();
				firstTime = logs.get(0).getTimestamp();
			}

			ServiceCourseDetails scd = new ServiceCourseDetails();
			final CourseObject courseObject = new CourseObject(course.getId(), course.getTitle(), course
					.getTitle(),
					participants.size(), lastTime, firstTime, scd.getCourseHash(course.getId(), firstTime, lastTime), StudentHelper.getGenderSupport(course.getId()));
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
		session.close();
		return new ResultListCourseObject(courses);
	}
	
	/**
	 * Returns the number of courses for the specified user.
	 * 
	 * @param id	User identifier
	 * 
	 * @return
	 */
	@GET
	@Path("/{uid}/coursecount")
	public Long getCourseCountForUser(@PathParam("uid") final Long id) {

		// Set up db-connection
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		Long r = ((Long) session.createQuery("Select count(*) from CourseUserMining where user="+id+" and role in (Select id from RoleMining where type in (0,1))").uniqueResult());
		session.close();
		return r;
	}

}
