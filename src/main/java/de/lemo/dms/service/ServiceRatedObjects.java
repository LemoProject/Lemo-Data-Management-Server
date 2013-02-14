/**
 * File ./main/java/de/lemo/dms/service/ServiceRatedObjects.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.List;
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
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.resulttype.ResultListStringObject;

/**
 * Service to get a list of all learning objects within the specified courses that have a grade attribute
 *
 */
@Path("ratedobjects")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRatedObjects extends BaseService {

	/**
	 * Returns a list of all learning objects within the specified courses that have a grade attribute (assignments,
	 * quizzes, scorms)
	 * 
	 * @param courses
	 *            Course-ids
	 * @return ResultList with 4 String elements per object(class name, class prefix, id, title)
	 */
	@GET
	public ResultListStringObject getRatedObjects(@QueryParam(MetaParam.COURSE_IDS) final List<Long> courses) {

		final ArrayList<String> res = new ArrayList<String>();

		try {
			// Set up db-connection
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();

			final Criteria criteria = session.createCriteria(ICourseRatedObjectAssociation.class, "aso");
			criteria.add(Restrictions.in("aso.course.id", courses));

			@SuppressWarnings("unchecked")
			final ArrayList<ICourseRatedObjectAssociation> list = (ArrayList<ICourseRatedObjectAssociation>) criteria
					.list();

			for (final ICourseRatedObjectAssociation obj : list)
			{
				//Simple name is not needed so far - will break App-server implementation !!!
				//res.add(obj.getRatedObject().getClass().getSimpleName());
				res.add(obj.getRatedObject().getPrefix().toString());
				res.add(obj.getRatedObject().getId() + "");
				res.add(obj.getRatedObject().getTitle());
			}

		} catch (final Exception e)
		{
			e.printStackTrace();
		}

		return new ResultListStringObject(res);
	}

}
