/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QPerformanceBoxPlot.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QPerformanceBoxPlot.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.abstractions.ILearningUserAssociation;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.BoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;
import de.lemo.dms.processing.resulttype.ResultListStringObject;
import de.lemo.dms.service.ServiceRatedObjects;

/**
 * Accumulates the performance (result of tests) of the users over a period
 * 
 * @author Sebastian Schwarzrock
 */
@Path("performanceboxplot")
public class QPerformanceBoxPlot extends Question {

	/**
	 * @param courses
	 *            (optional) List of course-ids that shall be included
	 * @param users
	 *            (optional) List of user-ids
	 * @param quizzes
	 *            (mandatory) List of the tuples: every learning object has successive entries, first the prefix of the
	 *            learning-object-type (11 for "assignment", 14 for "quiz", 17 for "scorm") and the objects id
	 * @param resolution
	 *            (mandatory)
	 * @param startTime
	 *            (mandatory)
	 * @param endTime
	 *            (mandatory)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListBoxPlot compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.QUIZ_IDS) List<Long> quizzes,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender) {

		validateTimestamps(startTime, endTime, resolution);
		
		if(logger.isDebugEnabled()) {
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
			logger.debug("Parameter list: Start time: : " + startTime);
			logger.debug("Parameter list: End time: : " + endTime);
		}
		
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
	

		final HashMap<Long, ArrayList<Double>> values = new HashMap<Long, ArrayList<Double>>();

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
		
		
		for (final ILearningUserAssociation aso : list)
		{
			final Long name = Long.valueOf(aso.getLearning().getId());
			if (values.get(name) == null)
			{
				final ArrayList<Double> v = new ArrayList<Double>();
				values.put(name, v);
			}

			values.get(name).add(aso.getFinalGrade() / ((maxGrades.get(aso.getLearning().getId()) / resolution)));
		}

		BoxPlot[] results = new BoxPlot[values.keySet().size()];

		int i = 0;
		for (final Entry<Long, ArrayList<Double>> e : values.entrySet())
		{

			final BoxPlot plotty = this.calcBox(e.getValue(),  e.getKey());
			results[i] = plotty;
			i++;
		}
		session.close();
		return new ResultListBoxPlot(Arrays.asList(results));
	}

	// berechnen der boxplot werte
	private BoxPlot calcBox(final List<Double> list, final Long id) {
		final BoxPlot result = new BoxPlot();
		result.setName(id + "");
		// ---SORTIEREN
		Collections.sort(list);
		// ---MEDIAN
		// gerade oder ungerade
		if ((list.size() % 2) == 0) {
			// gerade
			int uw, ow;
			uw = (list.size() / 2) - 1;
			ow = uw + 1;
			Double m = (list.get(uw) + list.get(ow));
			m = m / 2;
			result.setMedian(m);
		} else {
			// ungerade
			result.setMedian(list.get((list.size() / 2)));
		}
		// ---QUARTILE
		// 1 & 2Quartile
		long q1, q2;
		if (list.size() == 1) {
			q1 = 1;
			q2 = 1;
		} else {
			q1 = Math.round(0.25 * ((list.size()) + 1));
			q2 = Math.round(0.75 * ((list.size()) + 1));
		}
		final Long i1 = new Long(q1 - 1);
		final Long i2 = new Long(q2 - 1);
		result.setLowerQuartil(list.get(i1.intValue()));
		result.setUpperQuartil(list.get(i2.intValue()));
		result.setUpperWhisker(list.get(list.size() - 1));
		result.setLowerWhisker(list.get(0));

		return result;
	}

}
