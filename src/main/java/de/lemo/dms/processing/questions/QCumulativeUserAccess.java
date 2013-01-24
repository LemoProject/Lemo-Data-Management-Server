/**
 * File ./main/java/de/lemo/dms/processing/questions/QCumulativeUserAccess.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.DEGREE;
import static de.lemo.dms.processing.MetaParam.DEPARTMENT;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONException;
import org.hibernate.Session;
import de.lemo.dms.processing.BoxPlotGeneratorForDates;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.BoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;

@Path("cumulative")
public class QCumulativeUserAccess extends Question {

	/**
	 * @param minTimestamp
	 *            min time for the data
	 * @param maxTimestamp
	 *            max time for the data
	 * @param types
	 *            list with learning objects to compute
	 * @param departments
	 *            departments for the request
	 * @param degrees
	 *            degrees for the request
	 * @param course
	 *            courses for the request
	 * @return a list with the cumulative user access to the learning objects
	 * @throws SQLException
	 * @throws JSONException
	 */
	@POST
	// @Produces(MediaType.APPLICATION_JSON)
	public ResultListBoxPlot compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> course,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.DEPARTMENT) final List<Long> departments,
			@FormParam(MetaParam.DEGREE) final List<Long> degrees,
			@FormParam(MetaParam.START_TIME) final Long minTimestamp,
			@FormParam(MetaParam.END_TIME) final Long maxTimestamp) {

		super.logger.debug("call for question: cumulative user access");

		super.logger.debug("Activity types: " + types);
		final Set<ELearningObjectType> learnObjectTypes =
				ELearningObjectType.fromNames(types);

		// generiere querys
		final Map<ELearningObjectType, String> querys = this.generateQuerys(minTimestamp,
				maxTimestamp, learnObjectTypes, departments, degrees, course);

		super.logger.debug("Query result: " + querys.toString());

		final Session session = super.dbHandler.getMiningSession();

		// SQL querys
		super.logger.debug("Starting processing ....");
		final BoxPlotGeneratorForDates bpg = new BoxPlotGeneratorForDates();
		try {
			for (final ELearningObjectType lo : querys.keySet()) {
				super.logger.debug("Starting processing -- Entering try catch");
				@SuppressWarnings("deprecation")
				final
				Statement statement = session.connection().createStatement();
				final ResultSet set = statement.executeQuery(querys.get(lo));

				// durchlaufen des result sets
				while (set.next()) {
					bpg.addAccess(set.getLong("timestamp"));
				}
			}
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
	 * @param timestamp_min
	 *            Untergrenze für den Zeitraum
	 * @param timestamp_max
	 *            Obergrenze für den Zeitraum
	 * @param types
	 *            Learning Objects die erfasst werden sollen
	 * @return Liste mit Querys zu den LearningObjects
	 */
	private Map<ELearningObjectType, String> generateQuerys(
			final long timestamp_min, final long timestamp_max,
			final Set<ELearningObjectType> types,
			final List<Long> departments,
			final List<Long> degrees,
			final List<Long> course) {
		final HashMap<ELearningObjectType, String> result = new HashMap<ELearningObjectType, String>();
		boolean timeframe = false;
		final List<String> qa = new ArrayList<String>();

		// prüfen des zeitraums
		if ((timestamp_max != 0) && (timestamp_min != 0)
				&& (timestamp_max >= timestamp_min)) {
			timeframe = true;
		}
		for (final ELearningObjectType loType : types) {
			String query = this.generateBaseQuery(loType.name().toLowerCase());
			if (timeframe || (course != null) || (departments != null)
					|| (degrees != null)) {
				query += " WHERE";
			}
			// zeitraum
			if (timeframe) {
				qa.add(" timestamp >= " + timestamp_min + " AND timestamp<= "
						+ timestamp_max);
			}
			// filter departments
			if ((departments != null) && !departments.isEmpty()) {
				final StringBuilder sb = new StringBuilder();
				sb.append(" (");
				int i = 0;
				for (final Long dep : departments) {
					sb.append(" department.id = " + dep.toString());
					if (i < (departments.size() - 1)) {
						sb.append(" OR");
					}
					i++;
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			// filter degrees
			if ((degrees != null) && !degrees.isEmpty()) {
				final StringBuilder sb = new StringBuilder();
				sb.append(" (");
				int i = 0;
				for (final Long deg : degrees) {
					sb.append(" degree.id = " + deg.toString());
					if (i < (degrees.size() - 1)) {
						sb.append(" OR");
					}
					i++;
				}
				sb.append(")");
				qa.add(sb.toString());
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
		// return "SELECT "+ table +"_log.timestamp, user_id FROM "+ table
		// +" INNER JOIN "+ table +"_log ON " + table + ".id = " + table +
		// "_log."+ table +"_id";
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT timestamp, user_id, course.title as course, course.id as courseId, degree.title as degree, degree.id as degreeId, department.title AS department, department.id as departmentId");
		sb.append(" FROM ((((" + table + "_log AS log");
		sb.append(" LEFT JOIN course");
		sb.append(" ON course.id = log.course_id)");
		sb.append(" LEFT JOIN degree_course as dc");
		sb.append(" ON log.course_id = dc.course_id)");
		sb.append(" LEFT JOIN degree");
		sb.append(" ON dc.degree_id = degree.id)");
		sb.append(" LEFT JOIN department_degree as dg");
		sb.append(" ON degree.id = dg.degree_id)");
		sb.append(" LEFT JOIN department");
		sb.append(" ON dg.department_id = department.id");
		return sb.toString();
	}

}
