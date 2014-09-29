/**
 * File ./src/main/java/de/lemo/dms/service/ServiceLearningTypes.java
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
 * File ./main/java/de/lemo/dms/service/ServiceLearningTypes.java
 * Date 2014-09-24
 * Project Lemo Learning Analytics
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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.resulttype.ResultListStringObject;

/**
 * Service to get details for a single course or a list of courses.
 * 
 * @author Sebastian Schwarzrock
 */
@Path("learningtypes")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceLearningTypes {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Gets the available LearningTypes of the course.
	 * 
	 * @param id	Identifier of the course.
	 * 
	 * @return	A List of id-name-tupels of LearningTypes.
	 */
	@SuppressWarnings("unchecked")
	@GET
	public ResultListStringObject getLearningTypes(@QueryParam(MetaParam.COURSE_IDS) final List<Long> courses) {
		List<String> result = new ArrayList<String>();
		if( courses != null && !courses.isEmpty())
		{
			IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();
	
	
			Criteria criteria = session.createCriteria(CourseLearning.class, "courseLearning");
			criteria.add(Restrictions.in("courseLearning.course.id", courses));
			List<CourseLearning> cLList = criteria.list();
			try{
				for(CourseLearning cl : cLList)
				{
					if(!result.contains(cl.getLearning().getType().getType()))
					{
						result.add(cl.getLearning().getType().getId() + "");
						result.add(cl.getLearning().getType().getType());
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
					//dbHandler.closeSession(session);
			session.close();

		}
		ResultListStringObject r = new ResultListStringObject(result);
		return r;
	}

	

}
