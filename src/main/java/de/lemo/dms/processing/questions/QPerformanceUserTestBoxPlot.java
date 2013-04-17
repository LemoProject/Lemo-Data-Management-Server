/**
 * File ./main/java/de/lemo/dms/processing/questions/QPerformanceUserTestBoxPlot.java
 * Date 2013-03-01
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
import de.lemo.dms.db.miningDBclass.abstractions.IRatedLogObject;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.BoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;
import de.lemo.dms.processing.resulttype.ResultListStringObject;
import de.lemo.dms.service.ServiceRatedObjects;

/**
 * Gathers and returns all all test results for every student and every test in a course
 * 
 * @author Sebastian Schwarzrock
 */
@Path("performanceUserTestBoxPlot")
public class QPerformanceUserTestBoxPlot {
	
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
	public ResultListBoxPlot compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.QUIZ_IDS) final List<Long> quizzes,
			@FormParam(MetaParam.RESOLUTION) Long resolution,
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
		
		Criteria criteria;
		if(users == null || users.size() == 0)
		{
			users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses).values());
		}
		else
		{
			Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses);
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}
		
		criteria = session.createCriteria(IRatedLogObject.class, "log");
		criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
		if ((courses != null) && (courses.size() > 0)) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if(users != null && users.size() > 0)
			criteria.add(Restrictions.in("log.user.id", users));
		
		final ArrayList<IRatedLogObject> list = (ArrayList<IRatedLogObject>) criteria.list();

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

		
		final Map<String, IRatedLogObject> singleResults = new HashMap<String, IRatedLogObject>();
		Collections.sort(list);
		
		Set<Long> u = new HashSet<Long>();

		// This is for making sure there is just one entry per student and test
		for (int i = list.size() - 1; i >= 0; i--)
		{
			final IRatedLogObject log = list.get(i);

			final String key = log.getPrefix() + " " + log.getLearnObjId() + " " + log.getUser().getId();

			u.add(log.getUser().getId());
			
			if (log.getFinalGrade() != null && (singleResults.get(key) == null || log.getFinalGrade() > singleResults.get(key).getFinalGrade())) 
			{
				singleResults.put(key, log);

			}
		}		
		Map<Long, ArrayList<Double>> fin = new HashMap<Long, ArrayList<Double>>();
		
		for (final IRatedLogObject log : singleResults.values())
		{
			if ((obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())) != null)
					&& (log.getFinalGrade() != null) &&
					(log.getMaxGrade() != null) && (log.getMaxGrade() > 0))
			{
				Double step;
				// Determine size of each interval
				if(resolution == null || resolution == 0)
					resolution = 100L;
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
						ArrayList<Double> l = new ArrayList<Double>();
						for(int i = 0; i < quizzes.size(); i++)
						{
							l.add(-1d);
						}
						fin.put(log.getUser().getId(), l);
						fin.get(log.getUser().getId()).set(quizzes.indexOf(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())), pos.doubleValue());
					}
					else
					{
						fin.get(log.getUser().getId()).set(quizzes.indexOf(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())), pos.doubleValue());
					}
				}
			}
		}
		
		BoxPlot[] results = new BoxPlot[fin.keySet().size()];

		ArrayList<Double> c = new ArrayList<Double>();
		c.add(-1d);
		
		int i = 0;
		Map<Long, Long> idToAlias = StudentHelper.getCourseStudentsRealKeys(courses);
		for (final Entry<Long, ArrayList<Double>> e : fin.entrySet())
		{
			e.getValue().removeAll(c);
			final BoxPlot plotty = this.calcBox(e.getValue(), idToAlias.get(e.getKey()));
			results[i] = plotty;
			i++;
		}
		session.close();
		return new ResultListBoxPlot(Arrays.asList(results));
	}
	
	// calculation of box-plot values
		private BoxPlot calcBox(final List<Double> list, final Long id) {
			final BoxPlot result = new BoxPlot();
			result.setName(id + "");
			// ---sort
			Collections.sort(list);
			// ---MEDIAN
			// even or odd
			if ((list.size() % 2) == 0) {
				// even
				int uw, ow;
				uw = (list.size() / 2) - 1;
				ow = uw + 1;
				Double m = (list.get(uw) + list.get(ow));
				m = m / 2;
				result.setMedian(m);
			} else {
				// odd
				result.setMedian(list.get((list.size() / 2)));
			}
			// ---QUARTILE
			// 1 & 2 quartile
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
