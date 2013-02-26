/**
 * File ./main/java/de/lemo/dms/service/ServiceUserInformation.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

/**
 * Service for user information. Provide all courses for a user
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceUserInformation {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Path("/{uid}/courses")
	public ResultListCourseObject getCoursesByUser(@PathParam("uid") final long id,
			@QueryParam("course_count") final Long count,
			@QueryParam("course_offset") final Long offset) {
		this.logger.info("## " + id);
		final ArrayList<CourseObject> courses = new ArrayList<CourseObject>();

		// Set up db-connection
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		@SuppressWarnings("unchecked")
		final ArrayList<Long> cu = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.SQL,
				"Select course_id from course_user where user_id=" + id);

		String query = "";
		for (int i = 0; i < cu.size(); i++) {
			if (i == 0) {
				query += "(" + cu.get(i);
			} else {
				query += "," + cu.get(i);
			}
			if (i == (cu.size() - 1)) {
				query += ")";
			}
		}

		if (cu.size() > 0) {
			@SuppressWarnings("unchecked")
			final ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(session,
					EQueryType.HQL,
					"from CourseMining where id in " + query);
			for (int i = 0; i < ci.size(); i++) {
				@SuppressWarnings("unchecked")
				final ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
						"Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(i).getId());
				@SuppressWarnings("unchecked")
				final ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
						"Select max(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
				@SuppressWarnings("unchecked")
				final ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
						"Select min(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
				Long cpa = 0L;
				if ((parti.size() > 0) && (parti.get(0) != null)) {
					cpa = parti.get(0);
				}
				Long cla = 0L;
				if ((latest.size() > 0) && (latest.get(0) != null)) {
					cla = latest.get(0);
				}
				Long cfi = 0L;
				if ((first.size() > 0) && (first.get(0) != null)) {
					cfi = first.get(0);
				}
				final CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i)
						.getTitle(),
						cpa, cla, cfi);
				courses.add(co);
			}
		}
		dbHandler.closeSession(session);
		return new ResultListCourseObject(courses);
	}

}
