/**
 * File ./main/java/de/lemo/dms/service/ServiceRoles.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.processing.resulttype.ResultListLongObject;
@Path("teachercourses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceTeacherCourses extends BaseService {

	@GET
	public ResultListLongObject getTeachersCourses(Long id) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		ResultListLongObject result;
		
		final Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.eq("cu.user.id", id));
		
		ArrayList<CourseUserMining> results = (ArrayList<CourseUserMining>) criteria.list();

		if(results != null && results.size() > 0)
		{
			ArrayList<Long> l = new ArrayList<Long>();
			for(CourseUserMining cu : results)
				l.add(cu.getCourse().getId());
			result = new ResultListLongObject(l);
		}
		else
			result = new ResultListLongObject();	
		
		return result;
	}

}