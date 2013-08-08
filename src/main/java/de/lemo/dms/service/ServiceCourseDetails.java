/**
 * File ./src/main/java/de/lemo/dms/service/ServiceCourseDetails.java
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
 * File ./main/java/de/lemo/dms/service/ServiceCourseDetails.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.CourseMining;
import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.db.mapping.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;
import de.lemo.dms.service.responses.ResourceNotFoundException;

/**
 * Service to get details for a single course or a list of courses.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceCourseDetails {

	private Logger logger;

	/**
	 * Gets the details for a single course including id, title, description, 
	 * number of participants, time of first student-request, time of latest student-request.
	 * 
	 * @param id	Identifier of the course.
	 * 
	 * @return	A CourseObject containing the information.
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("{cid}")
	public CourseObject getCourseDetails(@PathParam("cid") final Long id) {

		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		CourseMining course = (CourseMining) session.get(CourseMining.class, id);
		if (course == null) {
			throw new ResourceNotFoundException("Course " + id);
		}

		final Criteria criteria = session.createCriteria(ILogMining.class, "log");
		List<Long> cid = new ArrayList<Long>();
		cid.add(id);
		List<Long> users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(cid, new ArrayList<Long>()).values());
		
		criteria.add(Restrictions.eq("log.course.id", id));
		if(users.size() > 0)
		{
			criteria.add(Restrictions.in("log.user.id", users));
		}
		ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
		Collections.sort(logs);

		Long lastTime = 0L;
		Long firstTime = 0L;

		if (logs.size() > 0)
		{
			lastTime = logs.get(logs.size() - 1).getTimestamp();
			firstTime = logs.get(0).getTimestamp();
		}

		CourseObject result =
				new CourseObject(course.getId(), course.getShortname(), course.getTitle(), users.size(), lastTime, firstTime, getCourseHash(id), StudentHelper.getGenderSupport(id));

		//dbHandler.closeSession(session);
		session.close();
		return result;
	}

	/**
	 * Gets the details for a a list of courses including id, title, description, 
	 * number of participants, time of first student-request, time of latest student-request.
	 * 
	 * @param id	List of course identifiers.
	 * 
	 * @return	A List of CourseObjects containing the information.
	 */
	@GET
	@Path("/multi")
	public ResultListCourseObject getCoursesDetails(@QueryParam(MetaParam.COURSE_IDS) final List<Long> courses) {

		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final ArrayList<CourseObject> results = new ArrayList<CourseObject>();

		if (courses.isEmpty()) {
			logger.debug("Courses List is empty");
			return new ResultListCourseObject(results);
		} else for(Long id : courses) logger.debug("Looking for Course: "+ id);

		// Set up db-connection
		final Session session = dbHandler.getMiningSession();

		Criteria criteria = session.createCriteria(CourseMining.class, "course");
		criteria.add(Restrictions.in("course.id", courses));

		@SuppressWarnings("unchecked")
		final ArrayList<CourseMining> ci = (ArrayList<CourseMining>) criteria.list();

		Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, new ArrayList<Long>());
		
		for (CourseMining courseMining : ci) {
			
			criteria = session.createCriteria(ILogMining.class, "log");
			criteria.add(Restrictions.eq("log.course.id", courseMining.getId()));
			if(userMap.size() > 0)
			{
				criteria.add(Restrictions.in("log.user.id", userMap.values()));
			}
			//TODO Redefine projection for max id as detachable criteria (Subselect)
			//DetachedCriteria sub = DetachedCriteria.forClass(ILogMining.class);
			//	sub.setProjection(Projections.max("id"));
			//
			//criteria.add(Restrictions.eq("id",sub));
				
			ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
			Collections.sort(logs);

			Long lastTime = 0L;
			Long firstTime = 0L;
			

			if (logs.size() > 0)
			{
				lastTime = logs.get(0).getTimestamp();
				firstTime = logs.get(logs.size() - 1).getTimestamp();
			}
			final CourseObject co = new CourseObject(courseMining.getId(), courseMining.getShortname(),
					courseMining.getTitle(), userMap.size(), lastTime, firstTime, getCourseHash(courseMining.getId()), StudentHelper.getGenderSupport(courseMining.getId()));
			results.add(co);
		}
		
		if (results.isEmpty()) {
			logger.debug("Result List is empty");
		} else for(CourseObject co : results) logger.debug("Result Course: "+ co.getDescription());
		session.close();
		return new ResultListCourseObject(results);
	}
	
	/**
	 * Gets the details for a a list of courses including id, title, description, 
	 * number of participants, time of first student-request, time of latest student-request.
	 * 
	 * @param id	List of course identifiers.
	 * 
	 * @return	A List of CourseObjects containing the information.
	 */
	@GET
	@Path("{cid}/hash")
	public Long getCourseHash(@PathParam("cid") final Long id) {
		
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		CourseMining course = (CourseMining) session.get(CourseMining.class, id);
		if (course == null) {
			throw new ResourceNotFoundException("Course " + id);
		}

		Criteria criteria = session.createCriteria(ILogMining.class, "log");
		List<Long> cid = new ArrayList<Long>();
		cid.add(id);
		List<Long> users = new ArrayList<Long>(StudentHelper.getCourseStudentsRealKeys(cid, new ArrayList<Long>()).values());
		
		criteria.add(Restrictions.eq("log.course.id", id));
		if(users.size() > 0)
		{
			criteria.add(Restrictions.in("log.user.id", users));
		}
		ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
		Collections.sort(logs);

		Long lastTime = 0L;
		Long firstTime = 0L;

		if (logs.size() > 0)
		{
			lastTime = logs.get(logs.size() - 1).getTimestamp();
			firstTime = logs.get(0).getTimestamp();
		}
		
		criteria = session.createCriteria(ICourseLORelation.class, "lor");
		criteria.add(Restrictions.eq("lor.course.id", id));
		ArrayList<ICourseLORelation> lor = (ArrayList<ICourseLORelation>) criteria.list();
		
		Long hash = lastTime * 13 + firstTime * 17 + 19 * users.hashCode() + 23 * lor.hashCode();
		//dbHandler.closeSession(session);
		session.close();
		
		return hash;
	}
	
	/**
	 * Gets the details for a a list of courses including id, title, description, 
	 * number of participants, time of first student-request, time of latest student-request.
	 * 
	 * @param id	List of course identifiers.
	 * 
	 * @return	A List of CourseObjects containing the information.
	 */
	@GET
	@Path("{cid}/genderSupport")
	public boolean getGenderSupport(@PathParam("cid") final Long id) {
		
		return StudentHelper.getGenderSupport(id);
	}

}
