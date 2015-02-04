/**
 * File ./src/main/java/de/lemo/dms/service/ServiceCourseTitleSearch.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./main/java/de/lemo/dms/service/ServiceCourseTitleSearch.java
 * Date 2013-03-15
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseAttribute;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

/**
 * Service to get details for all courses having titles matching the searched string
 * 
 * @author Sebastian Schwarzrock
 */
@Path("coursesbytext")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceCourseTitleSearch {
	
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Gets the details of all courses whose title matches search text. Informations include id, title, description, 
	 * number of participants, time of first student-request, time of latest student-request.
	 * 
	 * @param text	.
	 * 
	 * @return	A List of CourseObjects containing the information.
	 */
	@GET
	public ResultListCourseObject getCoursesByText(@QueryParam(MetaParam.SEARCH_TEXT) final String text,
			@QueryParam(MetaParam.RESULT_AMOUNT) final Long count,
			@QueryParam(MetaParam.OFFSET) final Long offset ) {
		
		logger.info("Started TitleSearch");

		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		List<CourseObject> result = new ArrayList<CourseObject>();
		result.add(new CourseObject());
		result.add(new CourseObject());

		if (text == null || text.equals("")) {
			return new ResultListCourseObject(result);
		}
		// Set up db-connection
		final Session session = dbHandler.getMiningSession();
		

		Criteria criteria = session.createCriteria(Course.class, "course");
		criteria.add(Restrictions.ilike("course.title", text, MatchMode.ANYWHERE));

		@SuppressWarnings("unchecked")
		final ArrayList<Course> courses = (ArrayList<Course>) criteria.list();
		
		criteria = session.createCriteria(Attribute.class, "attribute");
		criteria.add(Restrictions.like("attribute.name", "CourseFirstRequest"));
		
		Long fattId = -1l;
		
		if(criteria.list().size() > 0)
		{
			fattId = ((Attribute)criteria.list().get(criteria.list().size()-1)).getId();
		}
		
		criteria = session.createCriteria(Attribute.class, "attribute");
		criteria.add(Restrictions.like("attribute.name", "CourseLastRequest"));

		Long lattId = -1l;
		
		if(criteria.list().size() > 0)
		{
			lattId = ((Attribute)criteria.list().get(criteria.list().size()-1)).getId();
		}
		ServiceCourseDetails scd = new ServiceCourseDetails();
		
		int counter = 0;
		
		for (Course courseMining : courses) {
		
			Long lastTime = 0L;
			Long firstTime = 0L;
			
			criteria = session.createCriteria(CourseAttribute.class, "courseAttribute");
			criteria.add(Restrictions.eq("courseAttribute.course.id", courseMining.getId()));
			criteria.add(Restrictions.eq("courseAttribute.attribute.id", fattId));
			
			if(criteria.list().size() > 0)
				firstTime = Long.valueOf(((CourseAttribute)criteria.list().get(0)).getValue());
			
			criteria = session.createCriteria(CourseAttribute.class, "courseAttribute");
			criteria.add(Restrictions.eq("courseAttribute.course.id", courseMining.getId()));
			criteria.add(Restrictions.eq("courseAttribute.attribute.id", lattId));
			
			if(criteria.list().size() > 0)
				lastTime = Long.valueOf(((CourseAttribute)criteria.list().get(0)).getValue());
			
			if(lastTime == 0L)
			{
				criteria = session.createCriteria(ILog.class, "log");
				criteria.add(Restrictions.eq("log.course.id", courseMining.getId()));
			    criteria.setProjection( Projections.projectionList().add( Projections.max("log.timestamp")));			    
			    for(int i = 0; i < criteria.list().size(); i++)
			    {
			    	if(criteria.list().get(i) != null)
			    		lastTime = (Long)criteria.list().get(i);
			    }
			}
			if(firstTime == 0L)
			{			 
				criteria = session.createCriteria(ILog.class, "log");
				criteria.add(Restrictions.eq("log.course.id", courseMining.getId()));
				criteria.setProjection( Projections.projectionList().add( Projections.min("log.timestamp")));				    
			    for(int i = 0; i < criteria.list().size(); i++)
			    {
			    	if(criteria.list().get(i) != null)
			    		firstTime = (Long)criteria.list().get(i);
			    }
			}
			
			int scount = StudentHelper.getStudentCount(courseMining.getId());
			Long chash = scd.getCourseHash(courseMining.getId(), firstTime, lastTime);
			boolean gsupp = StudentHelper.getGenderSupport(courseMining.getId());

			final CourseObject co = new CourseObject(courseMining.getId(), courseMining.getTitle(),
					courseMining.getTitle(), scount, 
					lastTime, firstTime, chash, gsupp);
			result.add(co);
		}
		/*
		if(count != null && count > 0)
		{
			if(offset != null && offset > 0 )
			{
				if(result.size() - offset >= count)
				{
					result = result.subList(offset.intValue(), offset.intValue() + count.intValue());
				}
				else
				{
					result = result.subList(offset.intValue(), courses.size()-1);
				}
			}
			else
			{
				if(result.size() > count)
				{
					result = result.subList(0, count.intValue());
				}
			}
			
		}*/

		session.close();
		logger.info("Finished TitleSearch");

		
		return new ResultListCourseObject(result);
	}

}
