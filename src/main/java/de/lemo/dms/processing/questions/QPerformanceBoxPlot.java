/**
 * File ./main/java/de/lemo/dms/processing/questions/QPerformanceBoxPlot.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
import de.lemo.dms.processing.resulttype.BoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;

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
	@POST
	public ResultListBoxPlot compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) final List<Long> users,
			@FormParam(MetaParam.QUIZ_IDS) List<Long> quizzes,
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
		System.out.println("Parameter list: Start time: : " + startTime);
		System.out.println("Parameter list: End time: : " + endTime);

		if ((startTime == null) || (endTime == null))
		{
			System.out.println("Calculation aborted. At least one of the mandatory parameters is not set properly.");
			// return new ResultListBoxPlot();
		}
		if (quizzes == null) {
			quizzes = new ArrayList<Long>();
		}

		final HashMap<Long, ArrayList<Double>> values = new HashMap<Long, ArrayList<Double>>();
		BoxPlot[] results = null;
		try
		{
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
				if ((quizzes.size() == 0) || quizzes.contains(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())))
				{
					if ((singleResults.get(key) == null) && (log.getFinalGrade() != null)
							&& (log.getMaxGrade() != null))
					{
						if (values.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())) == null)
						{
							final ArrayList<Double> v = new ArrayList<Double>();
							values.put(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId()), v);
						}
						singleResults.put(key, log);
					}
				}
			}

			for (final IRatedLogObject log : singleResults.values())
			{
				final Long name = Long.valueOf(log.getPrefix() + "" + log.getLearnObjId());
				if (values.get(name) == null)
				{
					final ArrayList<Double> v = new ArrayList<Double>();
					values.put(name, v);
				}

				values.get(name).add(log.getFinalGrade() / ((log.getMaxGrade() / resolution)));
			}

			results = new BoxPlot[values.keySet().size()];

			int i = 0;
			for (final Entry<Long, ArrayList<Double>> e : values.entrySet())
			{

				final BoxPlot plotty = this.calcBox(e.getValue(), e.getKey());
				results[i] = plotty;
				i++;
			}
			/*
			 * for(int i = 0; i < values.keySet().size(); i++)
			 * {
			 * BoxPlot plotty = calcBox(values.get(quizzes.get(i)), quizzes.get(i));
			 * results[i] = plotty;
			 * }
			 */
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return new ResultListBoxPlot(Arrays.asList(results));
	}

	// berechnen der boxplot werte
	private BoxPlot calcBox(final ArrayList<Double> list, final Long id) {
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
		}
		else {
			// ungerade
			result.setMedian(list.get((list.size() / 2)));
		}
		// ---QUARTILE
		// 1 & 2Quartile
		long q1, q2;
		if (list.size() == 1) {
			q1 = 1;
			q2 = 1;
		}
		else {
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
