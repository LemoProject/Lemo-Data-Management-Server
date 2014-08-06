/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QPerformanceUserTest.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QPerformanceUserTest.java
 * Date 2013-02-26
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.mapping.abstractions.ILearningUserAssociation;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListLongObject;
import de.lemo.dms.processing.resulttype.ResultListStringObject;
import de.lemo.dms.service.ServiceRatedObjects;

/**
 * Gathers and returns all all test results for every student and every test in a course
 * 
 * @author Sebastian Schwarzrock
 */
@Path("performanceUserTest")
public class QPerformanceUserTest {
	
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * @param courses
	 *            (optional) List of course-ids that shall be included
	 * @param users
	 *            (optional) List of user-ids
	 * @param quizzes
	 *            (mandatory) List of learning object ids (the ids have to start with the type specific prefix (11 for
	 *            "assignment", 14 for "quiz", 17 for "scorm"))
	 * @param resolution
	 *            (mandatory) Used to scale the results. If set to 0, the method 
	 *            returns the actual results of the test. Otherwise it returns the 
	 *            results scaled using the value of resolution.
	 * @param startTime
	 *            (mandatory)
	 * @param endTime
	 *            (mandatory)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListLongObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.QUIZ_IDS) final List<Long> quizzes,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender) {

		if (logger.isDebugEnabled()) {
			if ((courses != null) && (courses.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Courses: " + courses.get(0));
				for (int i = 1; i < courses.size(); i++) {
					buffer.append(", " + courses.get(i));
				}
				logger.debug(buffer.toString());
			}
			if ((users != null) && (users.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Users: " + users.get(0));
				for (int i = 1; i < users.size(); i++) {
					buffer.append(", " + users.get(i));
				}
				logger.debug(buffer.toString());
			}
			logger.debug("Parameter list: Resolution: : " + resolution);
			logger.debug("Parameter list: Start time: : " + startTime);
			logger.debug("Parameter list: End time: : " + endTime);
		}

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria;
		if(users == null || users.size() == 0)
		{
			users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
		}
		else
		{
			Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}
		criteria = session.createCriteria(ILearningUserAssociation.class, "log");
		criteria.add(Restrictions.between("log.timemodified", startTime, endTime));
		if ((courses != null) && (courses.size() > 0)) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if ((users != null) && (users.size() > 0)) {
			criteria.add(Restrictions.in("log.user.id", users));
		}

		final ArrayList<ILearningUserAssociation> list = (ArrayList<ILearningUserAssociation>) criteria.list();


		final Map<Long, Integer> obj = new HashMap<Long, Integer>();

		if(quizzes.size() > 0)
		{
			for (int i = 0; i < quizzes.size(); i++)
			{
				obj.put(quizzes.get(i), i);
			}
		}
		else
		{
			ServiceRatedObjects sro = new ServiceRatedObjects();
			ResultListStringObject rso = sro.getRatedObjects(courses);
			String s = new String();
			int count = 0;
			for(int i = 0; i < rso.getElements().size(); i++)
			{
				if((i + 1) % 3 != 0)
				{
					s += rso.getElements().get(i);
				}
				else
				{
					obj.put(Long.valueOf(s), count);
					quizzes.add(Long.valueOf(s));
					s= "";
					count++;
				}
			}
			
		}
		
		Set<Long> u = new HashSet<Long>();

		// Determine length of result array
		final int objects = quizzes.size() + u.size() * quizzes.size() + u.size();

		final Long[] results = new Long[objects];
		
		Map<Long, ArrayList<Long>> fin = new HashMap<Long, ArrayList<Long>>();
		
		criteria = session.createCriteria(Attribute.class, "attribute");
		criteria.add(Restrictions.like("attribute.name", "MaxGrade"));
		Long maxGradeId;
		final Map<Long, Double> maxGrades = new HashMap<Long, Double>();
		if(!criteria.list().isEmpty())
		{
			maxGradeId = ((Attribute)criteria.list().get(0)).getId();
			criteria = session.createCriteria(LearningAttribute.class, "learningAttribute");
			criteria.add(Restrictions.in("learningAttribute.learning.id", obj.keySet()));
			criteria.add(Restrictions.eq("learningAttribute.attribute.id", maxGradeId));
			for(LearningAttribute la : (List<LearningAttribute>)criteria.list())
			{
				maxGrades.put(la.getLearning().getId(), Double.valueOf(la.getValue()));
			}
		}
		
		for (final ILearningUserAssociation association : list)
		{
			if ((obj.get(association.getLearning().getId()) != null)
					&& (maxGrades.get(association.getLearning().getId()) != null) && (maxGrades.get(association.getLearning().getId()) > 0))
			{
				Double step = 1d;
				// Determine size of each interval
				if(resolution != 0)
					step = maxGrades.get(association.getLearning().getId()) / resolution;
				if (step > 0d)
				{
					// Determine interval for specific grade
					Integer pos = (int) (association.getFinalGrade() / step);
					if (resolution > 0 && pos > (resolution - 1)) {
						pos = resolution.intValue() - 1;
					}
					
					if(fin.get(association.getUser().getId()) == null)
					{
						ArrayList<Long> l = new ArrayList<Long>();
						for(int i = 0; i < quizzes.size(); i++)
						{
							l.add(-1L);
						}
						fin.put(association.getUser().getId(), l);
						fin.get(association.getUser().getId()).set(quizzes.indexOf(association.getLearning().getId()), pos.longValue());
					}
					else
					{
						fin.get(association.getUser().getId()).set(quizzes.indexOf(association.getLearning().getId()), pos.longValue());
					}
				}

			}

		}
		
		criteria = session.createCriteria(ICourseRatedObjectAssociation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));
		
		
		ArrayList<ICourseRatedObjectAssociation> q = (ArrayList<ICourseRatedObjectAssociation>) criteria.list(); 
		HashMap<Long, Double> maxGrades2 = new HashMap<Long, Double>();
		for(ICourseRatedObjectAssociation aso : q)
		{
			maxGrades2.put(aso.getRatedObject().getId(), maxGrades.get(aso.getRatedObject().getId()));
		}
		
		//Determine maximum number of points for every quiz
		for(int i = 0; i < quizzes.size(); i++)
		{
			if(maxGrades2.get(quizzes.get(i)) != null)
				results[i] = maxGrades2.get(quizzes.get(i)).longValue();
			else
				results[i] = -1L;
		}
		
		int i = quizzes.size();
		Map<Long, Long> idToAlias = StudentHelper.getCourseStudentsRealKeys(courses, gender);
		for(Entry<Long, ArrayList<Long>> entry : fin.entrySet())
		{
			//Insert user-id into result list
			results[i] = idToAlias.get(entry.getKey());
			//Insert all test results for user to result list
			for(Long l : entry.getValue())
			{
				i++;
				results[i] = l;
			}
			i++;
		}
		session.close();
		return new ResultListLongObject(Arrays.asList(results));
	}

}
