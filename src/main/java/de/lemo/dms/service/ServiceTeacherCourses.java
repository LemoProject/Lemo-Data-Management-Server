/**
 * File ./main/java/de/lemo/dms/service/ServiceRoles.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * Service to get the courses of an teacher
 */
@Path("teachercourses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceTeacherCourses {

	/**
	 * Returns a list of ids for all courses of the teacher
	 * 
	 * @param id		User-id of the teacher. If set to null, the method will return all courses
	 * 					of the platform.
	 * @param startTime	Used to check if a course's 'enrolStart' is new enough. If set to null,
	 * 					no time-constraints will be used .
	 * 
	 * @return	List of course-ids
	 */
	@SuppressWarnings("unchecked")
	@GET
	public ResultListLongObject getTeachersCourses(@QueryParam(MetaParam.USER_IDS) Long id,
												   @QueryParam(MetaParam.START_TIME) Long startTime) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		ResultListLongObject result;

		ArrayList<Long> types = new ArrayList<Long>();
		types.add(0L);
		types.add(1L);

		final Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		if(id != null)
		{
			criteria.add(Restrictions.eq("cu.user.id", id));
			criteria.add(Restrictions.in("cu.role.type", types));
		}
		if(startTime != null)
		{
			criteria.add(Restrictions.ge("cu.enrolStart", startTime));
		}
		ArrayList<CourseUserMining> results = (ArrayList<CourseUserMining>) criteria.list();

		if (results != null && results.size() > 0)
		{
			ArrayList<Long> l = new ArrayList<Long>();
			for (CourseUserMining cu : results) {
				l.add(cu.getCourse().getId());
			}
			result = new ResultListLongObject(l);
		}
		else {
			result = new ResultListLongObject();
		}
		return result;
	}

}
