/**
 * File ./main/java/de/lemo/dms/processing/questions/QPerformanceUserTest.java
 * Date 2013-02-26
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedLogObject;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

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
	@POST
	public ResultListLongObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) final List<Long> users,
			@FormParam(MetaParam.QUIZ_IDS) final List<Long> quizzes,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		if (logger.isDebugEnabled()) {
			if ((courses != null) && (courses.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Courses: " + courses.get(0));
				for (int i = 1; i < courses.size(); i++) {
					buffer.append(", " + courses.get(i));
				}
				logger.info(buffer.toString());
			}
			if ((users != null) && (users.size() > 0))
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("Parameter list: Users: " + users.get(0));
				for (int i = 1; i < users.size(); i++) {
					buffer.append(", " + users.get(i));
				}
				logger.info(buffer.toString());
			}
			logger.info("Parameter list: Resolution: : " + resolution);
			logger.info("Parameter list: Start time: : " + startTime);
			logger.info("Parameter list: End time: : " + endTime);
		}

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria = session.createCriteria(IRatedLogObject.class, "log");
		criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
		if ((courses != null) && (courses.size() > 0)) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if ((users != null) && (users.size() > 0)) {
			criteria.add(Restrictions.in("log.user.id", users));
		}

		@SuppressWarnings("unchecked")
		final ArrayList<IRatedLogObject> list = (ArrayList<IRatedLogObject>) criteria.list();

		final Map<Long, Integer> obj = new HashMap<Long, Integer>();

		for (int i = 0; i < quizzes.size(); i++)
		{
			obj.put(quizzes.get(i), i);
		}

		
		final Map<String, IRatedLogObject> singleResults = new HashMap<String, IRatedLogObject>();
		Collections.sort(list);
		
		Set<Long> u = new HashSet<Long>();

		// This is for making sure there is just one entry per student and test
		for (int i = list.size() - 1; i >= 0; i--)
		{
			final IRatedLogObject log = list.get(i);

			final String key = log.getPrefix() + " " + log.getLearnObjId() + " " + log.getUser().getId();

			u.add(log.getUser().getId());
			
			if (singleResults.get(key) == null)
			{
				singleResults.put(key, log);
			}
		}
		
		// Determine length of result array
		final int objects = quizzes.size() + u.size() * quizzes.size() + u.size();

		final Long[] results = new Long[objects];
		
		Map<Long, ArrayList<Long>> fin = new HashMap<Long, ArrayList<Long>>();

		for (final IRatedLogObject log : singleResults.values())
		{
			if ((obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())) != null)
					&& (log.getFinalGrade() != null) &&
					(log.getMaxGrade() != null) && (log.getMaxGrade() > 0))
			{
				Double step = 1d;
				// Determine size of each interval
				if(resolution != 0)
					step = log.getMaxGrade() / resolution;
				if (step > 0d)
				{
					// Determine interval for specific grade
					Integer pos = (int) (log.getFinalGrade() / step);
					if (pos > (resolution - 1)) {
						pos = resolution.intValue() - 1;
					}
					
					if(fin.get(log.getUser().getId()) == null)
					{
						ArrayList<Long> l = new ArrayList<Long>();
						for(int i = 0; i < quizzes.size(); i++)
						{
							l.add(-1L);
						}
						fin.put(log.getUser().getId(), l);
						fin.get(log.getUser().getId()).set(quizzes.indexOf(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())), pos.longValue());
					}
					else
					{
						fin.get(log.getUser().getId()).set(quizzes.indexOf(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())), pos.longValue());
					}
				}

			}

		}
		
		criteria = session.createCriteria(ICourseRatedObjectAssociation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));
		
		@SuppressWarnings("unchecked")
		ArrayList<ICourseRatedObjectAssociation> q = (ArrayList<ICourseRatedObjectAssociation>) criteria.list(); 
		HashMap<Long, Double> maxGrades = new HashMap<Long, Double>();
		for(ICourseRatedObjectAssociation aso : q)
		{
			maxGrades.put(Long.valueOf(aso.getPrefix() + "" + aso.getRatedObject().getId()), aso.getRatedObject().getMaxGrade());
		}
		
		//Determine maximum number of points for every quiz
		for(int i = 0; i < quizzes.size(); i++)
		{
			if(maxGrades.get(quizzes.get(i)) != null)
				results[i] = maxGrades.get(quizzes.get(i)).longValue();
			else
				results[i] = -1L;
		}
		
		int i = quizzes.size();
		for(Entry<Long, ArrayList<Long>> entry : fin.entrySet())
		{
			//Insert user-id into result list
			results[i] = entry.getKey();
			//Insert all test results for user to result list
			for(Long l : entry.getValue())
			{
				i++;
				results[i] = l;
			}
			i++;
		}

		return new ResultListLongObject(Arrays.asList(results));
	}

}
