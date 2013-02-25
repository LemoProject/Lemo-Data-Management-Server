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
 * Service to get the courses from an docent
 */
@Path("teachercourses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceTeacherCourses {

	@GET
	public ResultListLongObject getTeachersCourses(@QueryParam(MetaParam.USER_IDS) Long id) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		ResultListLongObject result;

		ArrayList<Long> types = new ArrayList<Long>();
		types.add(0L);
		types.add(1L);

		final Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.eq("cu.user.id", id));
		criteria.add(Restrictions.in("cu.role.type", types));

		ArrayList<CourseUserMining> results = (ArrayList<CourseUserMining>) criteria.list();

		if (results != null && results.size() > 0)
		{
			ArrayList<Long> l = new ArrayList<Long>();
			for (CourseUserMining cu : results)
				l.add(cu.getCourse().getId());
			result = new ResultListLongObject(l);
		}
		else
			result = new ResultListLongObject();

		return result;
	}

}
