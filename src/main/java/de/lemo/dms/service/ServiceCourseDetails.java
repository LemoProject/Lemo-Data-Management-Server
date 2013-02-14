/**
 * File ./main/java/de/lemo/dms/service/ServiceCourseDetails.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

/**
 * Service to get details of a course
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 *
 */
@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceCourseDetails extends BaseService {

	@GET
	@Path("{cid}")
	public CourseObject getCourseDetails(@PathParam("cid") final Long id) {

		// Set up db-connection
		final Session session = this.dbHandler.getMiningSession();
		
		
		//
		
		
		
		//

		@SuppressWarnings("unchecked")
		final ArrayList<CourseMining> ci = (ArrayList<CourseMining>) this.dbHandler.performQuery(session,
				EQueryType.HQL,
				"from CourseMining where id = " + id);
		CourseObject co = new CourseObject();
		if ((ci != null) && (ci.size() >= 1)) {
			@SuppressWarnings("unchecked")
			final ArrayList<Long> parti = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(0).getId());
			final Criteria criteria = session.createCriteria(ILogMining.class, "log");
			criteria.add(Restrictions.eq("log.course.id", id));
			
			ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
			Collections.sort(logs);
			
			Long cla = 0L;
			Long cfin = 0L;
			
			if(logs.size() > 0)
			{
				cla = logs.get(logs.size()-1).getTimestamp();
				cfin = logs.get(0).getTimestamp();
			}
			
			Long cpan = 0L;
			if ((parti.size() > 0) && (parti.get(0) != null)) {
				cpan = parti.get(0);
			}
			
			co = new CourseObject(ci.get(0).getId(), ci.get(0).getShortname(), ci.get(0).getTitle(), cpan, cla, cfin);
		}
		this.dbHandler.closeSession(session);
		return co;
	}

	@GET
	public ResultListCourseObject getCoursesDetails(@QueryParam("course_id") final List<Long> ids) {

		final ArrayList<CourseObject> courses = new ArrayList<CourseObject>();

		if (ids.isEmpty()) {
			return new ResultListCourseObject(courses);
		}

		// Set up db-connection
		final Session session = this.dbHandler.getMiningSession();

		Criteria criteria = session.createCriteria(CourseMining.class, "course");
		criteria.add(Restrictions.in("course.id", ids));
		
		final ArrayList<CourseMining> ci = (ArrayList<CourseMining>) criteria.list();

		for (CourseMining courseMining : ci) {
			@SuppressWarnings("unchecked")
			final ArrayList<Long> parti = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select count(DISTINCT user) from CourseUserMining where course=" + courseMining.getId());
			
			criteria = session.createCriteria(ILogMining.class, "log");
			criteria.add(Restrictions.eq("log.course.id", courseMining.getId()));
			
			ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
			Collections.sort(logs);
			
			Long clan = 0L;
			Long cfin = 0L;
			
			if(logs.size() > 0)
			{
				clan = logs.get(logs.size()-1).getTimestamp();
				cfin = logs.get(0).getTimestamp();
			}
			Long cpan = 0L;
			if ((parti.size() > 0) && (parti.get(0) != null)) {
				cpan = parti.get(0);
			}
			final CourseObject co = new CourseObject(courseMining.getId(), courseMining.getShortname(), courseMining.getTitle(),
					cpan,
					clan, cfin);
			courses.add(co);
		}
		return new ResultListCourseObject(courses);
	}

}
