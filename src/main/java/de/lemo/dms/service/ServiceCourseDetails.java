/**
 * File ./main/java/de/lemo/dms/service/ServiceCourseDetails.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.miningDBclass.CourseMining;
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

		@SuppressWarnings("unchecked")
		final ArrayList<CourseMining> ci = (ArrayList<CourseMining>) this.dbHandler.performQuery(session,
				EQueryType.HQL,
				"from CourseMining where id = " + id);
		CourseObject co = new CourseObject();
		if ((ci != null) && (ci.size() >= 1)) {
			@SuppressWarnings("unchecked")
			final ArrayList<Long> parti = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(0).getId());
			@SuppressWarnings("unchecked")
			final ArrayList<Long> latest = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select max(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(0).getId());
			@SuppressWarnings("unchecked")
			final ArrayList<Long> first = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select min(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(0).getId());
			Long cpan = 0L;
			if ((parti.size() > 0) && (parti.get(0) != null)) {
				cpan = parti.get(0);
			}
			Long cla = 0L;
			if ((latest.size() > 0) && (latest.get(0) != null)) {
				cla = latest.get(0);
			}
			Long cfin = 0L;
			if ((first.size() > 0) && (first.get(0) != null)) {
				cfin = first.get(0);
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

		String query = "";
		for (int i = 0; i < ids.size(); i++) {
			if (i == 0) {
				query += "(" + ids.get(i);
			} else {
				query += "," + ids.get(i);
			}
			if (i == (ids.size() - 1)) {
				query += ")";
			}
		}

		@SuppressWarnings("unchecked")
		final ArrayList<CourseMining> ci = (ArrayList<CourseMining>) this.dbHandler.performQuery(session,
				EQueryType.HQL,
				"from CourseMining where id in " + query);

		for (int i = 0; i < ci.size(); i++) {
			@SuppressWarnings("unchecked")
			final ArrayList<Long> parti = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(i).getId());
			@SuppressWarnings("unchecked")
			final ArrayList<Long> latest = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select max(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
			@SuppressWarnings("unchecked")
			final ArrayList<Long> first = (ArrayList<Long>) this.dbHandler.performQuery(session, EQueryType.HQL,
					"Select min(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
			Long cpan = 0L;
			if ((parti.size() > 0) && (parti.get(0) != null)) {
				cpan = parti.get(0);
			}
			Long clan = 0L;
			if ((latest.size() > 0) && (latest.get(0) != null)) {
				clan = latest.get(0);
			}
			Long cfin = 0L;
			if ((first.size() > 0) && (first.get(0) != null)) {
				cfin = first.get(0);
			}
			final CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i).getTitle(),
					cpan,
					clan, cfin);
			courses.add(co);
		}
		return new ResultListCourseObject(courses);
	}

}
