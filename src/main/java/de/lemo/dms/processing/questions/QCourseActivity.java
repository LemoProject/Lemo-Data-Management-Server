/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QCourseActivity.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QCourseActivity.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListHashMapObject;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * Shows to the activities in the courses by objects
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("courseactivity")
public class QCourseActivity extends Question {

	/**
	 * Returns a list with the length of 'resolution'. Each entry holds the number of requests in the interval.
	 * 
	 * @param courses
	 *            (Mandatory) Course-identifiers of the courses that should be processed.
	 * @param roles
	 *            (Optional) Role-identifiers
	 * @param startTime
	 *            (Mandatory)
	 * @param endTime
	 *            (Mandatory)
	 * @param resolution
	 *            (Mandatory)
	 * @param resourceTypes
	 *            (Optional)
	 * @return
	 */
	@POST
	public ResultListHashMapObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.TYPES) final List<String> resourceTypes,
			@FormParam(MetaParam.GENDER) List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) List<Long> learningObjects){

		validateTimestamps(startTime, endTime, resolution);
		final Map<Long, ResultListLongObject> result = new HashMap<Long, ResultListLongObject>();
		Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
		// Set up db-connection
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		if (users.isEmpty()) {
			users = new ArrayList<Long>(userMap.values());
			if (users.isEmpty()) {
				// TODO no users in course, maybe send some http error
				this.logger.debug("No users found for course. Returning empty resultset.");
				return new ResultListHashMapObject();
			}
		}
		else
		{
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}

		// Calculate size of time intervalls
		final double intervall = (endTime - startTime) / (resolution);
		final Map<Long, Long> idToAlias = StudentHelper.getCourseStudentsRealKeys(courses, gender);
		final Map<Long, HashMap<Integer, Set<Long>>> userPerResStep = new HashMap<Long, HashMap<Integer, Set<Long>>>();

		// Create and initialize array for results
		for (int j = 0; j < courses.size(); j++)
		{

			final Long[] resArr = new Long[resolution.intValue()];
			for (int i = 0; i < resArr.length; i++)
			{
				resArr[i] = 0L;
			}
			final List<Long> l = new ArrayList<Long>();
			Collections.addAll(l, resArr);
			result.put(courses.get(j), new ResultListLongObject(l));
		}

		for (final Long course : courses) {
			userPerResStep.put(course, new HashMap<Integer, Set<Long>>());
		}

		for (String resourceType : resourceTypes) {
			this.logger.debug("Course Activity Request - CA Selection: " + resourceType);
		}
		if (resourceTypes.isEmpty()) {
			this.logger.debug("Course Activity Request - CA Selection: NO Items selected ");
		}

		final Criteria criteria = session.createCriteria(ILog.class, "log")
				.add(Restrictions.in("log.course.id", courses))
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.in("log.user.id", users));
		
		if(!learningObjects.isEmpty())
		{
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}

		@SuppressWarnings("unchecked")
		List<ILog> logs = criteria.list();

		for (ILog log : logs)
		{
			boolean isInRT = false;
			if ((resourceTypes != null) && (resourceTypes.size() > 0) && resourceTypes.contains(log.getLearning().getLOType()))
			{
				isInRT = true;
			}
			if ((resourceTypes == null) || (resourceTypes.size() == 0) || isInRT)
			{
				Integer pos = new Double((log.getTimestamp() - startTime) / intervall).intValue();
				if (pos > (resolution - 1)) {
					pos = resolution.intValue() - 1;
				}
				result.get(log.getCourse().getId()).getElements()
						.set(pos, result.get(log.getCourse().getId()).getElements().get(pos) + 1);
				if (userPerResStep.get(log.getCourse().getId()).get(pos) == null)
				{
					final Set<Long> s = new HashSet<Long>();
					s.add(idToAlias.get(log.getUser().getId()));
					userPerResStep.get(log.getCourse().getId()).put(pos, s);
				} else {
					userPerResStep.get(log.getCourse().getId()).get(pos).add(idToAlias.get(log.getUser().getId()));
				}
			}
		}

		for (final Long c : courses)
		{
			for (int i = 0; i < resolution; i++)
			{
				if (userPerResStep.get(c).get(i) == null)
				{
					result.get(c).getElements().add(0L);
				} else {
					result.get(c).getElements().add(Long.valueOf(userPerResStep.get(c).get(i).size()));
				}
			}
		}

		final ResultListHashMapObject resultObject = new ResultListHashMapObject(result);
		if ((resultObject != null) && (resultObject.getElements() != null)) {
			final Set<Long> keySet = resultObject.getElements().keySet();
			final Iterator<Long> it = keySet.iterator();
			while (it.hasNext()) 
			{
				final Long learnObjectTypeName = it.next();
				this.logger.info("Result Course IDs: " + learnObjectTypeName);
			}

		} else {
			this.logger.info("Returning empty resultset.");
		}
		session.close();
		return resultObject;
	}
}
