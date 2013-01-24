/**
 * File ./main/java/de/lemo/dms/processing/questions/QPerformanceHistogram.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.QUIZ_IDS;
import static de.lemo.dms.processing.MetaParam.RESOLUTION;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.USER_IDS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedLogObject;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@Path("performanceHistogram")
public class QPerformanceHistogram extends Question {

	/**
	 * @param courses
	 *            (optional) List of course-ids that shall be included
	 * @param users
	 *            (optional) List of user-ids
	 * @param quizzes
	 *            (mandatory) List of learning object ids (the ids have to start with the type specific prefix (11 for
	 *            "assignment", 14 for "quiz", 17 for "scorm"))
	 * @param resolution
	 *            (mandatory)
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
			@FormParam(MetaParam.RESOLUTION) final int resolution,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		if ((courses != null) && (courses.size() > 0))
		{
			System.out.print("Parameter list: Courses: " + courses.get(0));
			for (int i = 1; i < courses.size(); i++) {
				System.out.print(", " + courses.get(i));
			}
			System.out.println();
		}
		if ((users != null) && (users.size() > 0))
		{
			System.out.print("Parameter list: Users: " + users.get(0));
			for (int i = 1; i < users.size(); i++) {
				System.out.print(", " + users.get(i));
			}
			System.out.println();
		}
		System.out.println("Parameter list: Resolution: : " + resolution);
		System.out.println("Parameter list: Start time: : " + startTime);
		System.out.println("Parameter list: End time: : " + endTime);

		if ((quizzes == null) || (quizzes.size() < 1) || (resolution <= 0) || (startTime == null) || (endTime == null))
		{
			System.out.println("Calculation aborted. At least one of the mandatory parameters is not set properly.");
			return new ResultListLongObject();
		}

		// Determine length of result array
		final int objects = resolution * quizzes.size();

		final Long[] results = new Long[objects];
		// Initialize result array
		for (int i = 0; i < results.length; i++) {
			results[i] = 0L;
		}
		try
		{
			final HashMap<Long, Integer> obj = new HashMap<Long, Integer>();

			for (int i = 0; i < quizzes.size(); i++)
			{
				obj.put(quizzes.get(i), i);
			}

			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();

			final Criteria criteria = session.createCriteria(IRatedLogObject.class, "log");
			criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
			if ((courses != null) && (courses.size() > 0)) {
				criteria.add(Restrictions.in("log.course.id", courses));
			}
			if ((users != null) && (users.size() > 0)) {
				criteria.add(Restrictions.in("log.user.id", users));
			}

			@SuppressWarnings("unchecked")
			final ArrayList<IRatedLogObject> list = (ArrayList<IRatedLogObject>) criteria.list();

			final HashMap<String, IRatedLogObject> singleResults = new HashMap<String, IRatedLogObject>();
			Collections.sort(list);

			// This is for making sure there is just one entry per student and test
			for (int i = list.size() - 1; i >= 0; i--)
			{
				final IRatedLogObject log = list.get(i);

				final String key = log.getPrefix() + " " + log.getLearnObjId() + " " + log.getUser().getId();

				if (singleResults.get(key) == null)
				{
					singleResults.put(key, log);
				}
			}

			for (final IRatedLogObject log : singleResults.values())
			{
				if ((obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())) != null)
						&& (log.getFinalGrade() != null) &&
						(log.getMaxGrade() != null) && (log.getMaxGrade() > 0))
				{
					// Determine size of each interval
					final Double step = log.getMaxGrade() / resolution;
					if (step > 0d)
					{
						// Determine interval for specific grade
						int pos = (int) (log.getFinalGrade() / step);
						if (pos > (resolution - 1)) {
							pos = resolution - 1;
						}
						// Increase count of specified interval
						results[(resolution * obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId()))) + pos] = results[(resolution * obj
								.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId()))) + pos] + 1;
					}

				}

			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return new ResultListLongObject(Arrays.asList(results));
	}

}
