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

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONException;
import org.hibernate.Session;

import de.lemo.dms.core.LearningObjects;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.processing.BoxPlotGeneratorForDates;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.BoxPlot;
import de.lemo.dms.processing.resulttype.ResultListBoxPlot;

@Path("cumulative")
public class QCumulativeUserAccess extends Question {

	/**
	 * @param timestamp_min
	 *            min time for the data
	 * @param timestamp_max
	 *            max time for the data
	 * @param lol
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
	//@Produces(MediaType.APPLICATION_JSON)
	public ResultListBoxPlot compute(
			@FormParam(COURSE_IDS) List<Long> course,
			@FormParam(TYPES) List<String> lol,
			@FormParam(DEPARTMENT) List<Long> departments, 
			@FormParam(DEGREE) List<Long> degrees, 
			@FormParam(START_TIME) Long timestamp_min,
			@FormParam(END_TIME) Long timestamp_max) { 

		super.logger.info("call for question: cumulative user access");
		
		BoxPlotGeneratorForDates bpg = new BoxPlotGeneratorForDates();
		// ergebnis
		ResultListBoxPlot rlbp;
		List<LearningObjects> los = new ArrayList<LearningObjects>();
		
		super.logger.info("Starting activity type transformation ...");
		if(lol !=null && !lol.isEmpty()){
			for(int i = 0; i<lol.size();i++) {
				super.logger.info("Activity types: "+lol.get(i).toLowerCase());
				los.add(LearningObjects.valueOf(lol.get(i).toLowerCase()));
			}
		} else {
			super.logger.info("No activity types selected ... looking for all types");
			for(LearningObjects loValues : LearningObjects.values() )
				los.add(loValues);	
		}
		
		// generiere querys
		HashMap<LearningObjects, String> querys = generateQuerys(timestamp_min,
				timestamp_max, los, departments, degrees, course);

		super.logger.info("Query result: "+querys.toString());
		
		IDBHandler dbHandler = ServerConfiguration.getInstance()
				.getMiningDbHandler();
		Session session = dbHandler.getMiningSession();

		// SQL querys
		super.logger.info("Starting processing ....");
		
			try {
				for (LearningObjects lo : querys.keySet()) {
					super.logger.info("Starting processing -- Entering try catch....");
					@SuppressWarnings("deprecation")
					Statement statement = session.connection().createStatement();
					ResultSet set = statement.executeQuery(querys.get(lo));
	
					// durchlaufen des result sets
					while (set.next()) {
						bpg.addAccess(set.getLong("timestamp"));
					}
				}
				BoxPlot[] bp;
				bp = bpg.calculateResult();
				List<BoxPlot> l = new ArrayList<BoxPlot>();
				for(int i = 0; i< bp.length; i++) {
					l.add(bp[i]);
				}
				rlbp = new ResultListBoxPlot(l);
				super.logger.info("Resultlist created ...."+ rlbp.toString()+ " Number of entries: "+rlbp.getElements().size());
				return rlbp;
			} catch (Exception e) {
				// TODO Fehler korrekt loggen
				System.out.println("Exception at cumulativeUserAccess "
						+ e.getMessage());
				// bei fehler
				rlbp = new ResultListBoxPlot();
				return rlbp;
			}
		
	}

	/**
	 * Generiert die Querys für die Zusammenfassung der LearningObjects
	 * Insbesondere der WHERE Klausel
	 * 
	 * @param timestamp_min Untergrenze für den Zeitraum
	 * @param timestamp_max Obergrenze für den Zeitraum
	 * @param los Learning Objects die erfasst werden sollen
	 * @return Liste mit Querys zu den LearningObjects
	 */
	private HashMap<LearningObjects, String> generateQuerys(long timestamp_min, long timestamp_max, List<LearningObjects> los, List<Long> departments, List<Long> degrees, List<Long> course) {
		HashMap<LearningObjects, String> result = new HashMap<LearningObjects, String>();
		boolean timeframe = false;
		List<String> qa = new ArrayList<String>();
		
		//prüfen des zeitraums
		if(timestamp_max != 0 && timestamp_min != 0 && timestamp_max >= timestamp_min) {
			timeframe = true;
		}
		for(LearningObjects lo : los) {
			String query = generateBaseQuery(lo.name());
			if(timeframe ||course != null || departments != null || degrees != null) {
					query += " WHERE";
			}
			//zeitraum
			if(timeframe) {
				qa.add(" timestamp >= "+timestamp_min+ " AND timestamp<= "+timestamp_max);
			}
			//filter departments
			if(departments != null && !departments.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append(" (");
				int i = 0;
				for(Long dep : departments) {
					sb.append(" department.id = "+dep.toString());
					if(i < departments.size() -1) {
						sb.append(" OR");
					}
					i++;
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			//filter degrees
			if(degrees != null && !degrees.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append(" (");
				int i = 0;
				for(Long deg: degrees) {
					sb.append(" degree.id = "+deg.toString());
					if(i < degrees.size() -1) {
						sb.append(" OR");
					}
					i++;
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			//filter course
			if(course != null && !course.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				int i = 0;
				for(Long co: course) {
					sb.append(" course.id = "+co.toString());
					if(i < course.size() -1) {
						sb.append(" OR");
					}
					i++;
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			//klauseln für filter hinzufügen
			for(int i = 0; i < qa.size(); i++) {
				query += qa.get(i);
				if(i < qa.size()-1) {
					query += " AND";
				}
			}
			
			result.put(lo,query);
		}
		
		return result;
	}
	
	//generiert ein einfaches query mit einem inner join für die abfrage
	private String generateBaseQuery(String table) {
		//return "SELECT "+ table +"_log.timestamp, user_id FROM "+ table +" INNER JOIN "+ table +"_log ON " + table + ".id = " + table + "_log."+ table +"_id";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT timestamp, user_id, course.title as course, course.id as courseId, degree.title as degree, degree.id as degreeId, department.title AS department, department.id as departmentId");
		sb.append(" FROM (((("+table+"_log AS log");
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
