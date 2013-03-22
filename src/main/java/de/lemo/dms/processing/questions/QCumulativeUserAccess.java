/**
 * File ./main/java/de/lemo/dms/processing/questions/QCumulativeUserAccess.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.BoxPlotGeneratorForDates;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.BoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;

/**
 * Accumulates the requests of the users to the objects over a period
 * 
 * @author Boris Wenzlaff
 */
@Path("cumulative")
public class QCumulativeUserAccess extends Question {

	/**
	 * @param startTime
	 *            min time for the data
	 * @param endTime
	 *            max time for the data
	 * @param types
	 *            list with learning objects to compute
	 * @param course
	 *            courses for the request
	 * @return a list with the cumulative user access to the learning objects
	 * @throws SQLException
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListBoxPlot compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> course,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		validateTimestamps(startTime, endTime);

		final Set<ELearningObjectType> learnObjectTypes = ELearningObjectType.fromNames(types);

		// generiere querys
		final Map<ELearningObjectType, String> querys = this.generateQuerys(startTime,
				endTime, learnObjectTypes, course);

		super.logger.debug("Query result: " + querys.toString());

		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		// SQL querys
		super.logger.debug("Starting processing ....");
		final BoxPlotGeneratorForDates bpg = new BoxPlotGeneratorForDates();
		try {
			
			Criteria criteria = session.createCriteria(ILogMining.class, "log");
			criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
			criteria.add(Restrictions.in("log.course.id", course));
			List<ILogMining> logs = criteria.list();
			
			for(ILogMining log : logs)
			{
				String type = log.getClass().getSimpleName().toUpperCase();
				if(type.indexOf("LOG") > -1)
				{	
					type = type.substring(0, type.indexOf("LOG"));
					if(types.isEmpty() || types.contains(type))
						bpg.addAccess(log.getTimestamp());
				}
			}
			logs.clear();
			/*
			for (final ELearningObjectType lo : querys.keySet()) {
				super.logger.debug("Starting processing -- Entering try catch");
				@SuppressWarnings("deprecation")
				final Statement statement = session.connection().createStatement();
				final ResultSet set = statement.executeQuery(querys.get(lo));
				
				//-----------------
				

				
				//-------------------------

				// durchlaufen des result sets
				while (set.next()) {
					bpg.addAccess(set.getLong("timestamp"));
				}
			}
			*/
			final BoxPlot[] bp = bpg.calculateResult();
			final List<BoxPlot> l = new ArrayList<BoxPlot>();
			for (int i = 0; i < bp.length; i++) {
				l.add(bp[i]);
			}
			final ResultListBoxPlot rlbp = new ResultListBoxPlot(l);
			super.logger.debug("Resultlist created ...." + rlbp.toString()
					+ " Number of entries: " + rlbp.getElements().size());
			return rlbp;
		} catch (final Exception e) {
			this.logger.error(e);
			return new ResultListBoxPlot();
		}

	}

	/**
	 * Generiert die Querys für die Zusammenfassung der LearningObjects
	 * Insbesondere der WHERE Klausel
	 * 
	 * @param timestampMin
	 *            Untergrenze für den Zeitraum
	 * @param timestampMax
	 *            Obergrenze für den Zeitraum
	 * @param types
	 *            Learning Objects die erfasst werden sollen
	 * @return Liste mit Querys zu den LearningObjects
	 */
	private Map<ELearningObjectType, String> generateQuerys(
			final long timestampMin, final long timestampMax,
			final Set<ELearningObjectType> types,
			final List<Long> course) {
		final Map<ELearningObjectType, String> result = new HashMap<ELearningObjectType, String>();
		boolean timeframe = false;
		final List<String> qa = new ArrayList<String>();

		// prüfen des zeitraums
		if ((timestampMax != 0) && (timestampMin != 0)
				&& (timestampMax >= timestampMin)) {
			timeframe = true;
		}
		for (final ELearningObjectType loType : types) {
			String query = this.generateBaseQuery(loType.name().toLowerCase());
			if (timeframe || (course != null)) {
				query += " WHERE";
			}
			// zeitraum
			if (timeframe) {
				qa.add(" timestamp >= " + timestampMin + " AND timestamp<= "
						+ timestampMax);
			}
			// filter course
			if ((course != null) && !course.isEmpty()) {
				final StringBuilder sb = new StringBuilder();
				sb.append("(");
				int i = 0;
				for (final Long co : course) {
					sb.append(" course.id = " + co.toString());
					if (i < (course.size() - 1)) {
						sb.append(" OR");
					}
					i++;
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			// klauseln für filter hinzufügen
			for (int i = 0; i < qa.size(); i++) {
				query += qa.get(i);
				if (i < (qa.size() - 1)) {
					query += " AND";
				}
			}
			result.put(loType, query);
		}

		return result;
	}

	// generiert ein einfaches query mit einem inner join für die abfrage
	private String generateBaseQuery(final String table) {
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT timestamp, user_id, course.title as course, course.id as courseId");
		sb.append(" FROM " + table + "_log AS log");
		sb.append(" LEFT JOIN course");
		sb.append(" ON course.id = log.course_id");
		return sb.toString();
	}

}
