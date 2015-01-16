/**
 * File ./src/main/java/de/lemo/dms/service/ServiceRatedObjects.java
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
 * File ./main/java/de/lemo/dms/service/ServiceRatedObjects.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.resulttype.ResultListStringObject;

/**
 * Service to get a list of all learning objects within the specified courses that have a grade attribute
 */
@Path("ratedobjects")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRatedObjects {

	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * Returns a list of all learning objects within the specified courses that have a grade attribute (assignments,
	 * quizzes, scorms)
	 * 
	 * @param courses
	 *            Course-ids
	 * @return ResultList with 2 String elements per object(cid, title)
	 */
	@GET
	public ResultListStringObject getRatedObjects(@QueryParam(MetaParam.COURSE_IDS) final List<Long> courses) {

		final ArrayList<String> res = new ArrayList<String>();

		try {
			// Set up db-connection
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();

			final Criteria criteria = session.createCriteria(ICourseLORelation.class, "aso");
			criteria.add(Restrictions.in("aso.course.id", courses));

			@SuppressWarnings("unchecked")
			final ArrayList<ICourseLORelation> list = (ArrayList<ICourseLORelation>) criteria
					.list();

			for (final ICourseLORelation obj : list)
			{
				if(obj.getLearning().getInteractionType().toUpperCase().equals("ASSESSMENT"))
				{
					res.add(obj.getLearning().getId() + "");
					res.add(obj.getLearning().getTitle());
				}
			}
			session.close();

		} catch (final Exception e)
		{
			logger.error(e.getMessage());
		}
		return new ResultListStringObject(res);
	}

}
