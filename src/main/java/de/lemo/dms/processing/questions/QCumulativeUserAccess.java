package de.lemo.dms.processing.questions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Session;
import de.lemo.dms.core.LearningObjects;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.processing.Question;

@Path("cumulative")
public class QCumulativeUserAccess extends Question {

	/**
	 * 
	 * @param timestamp_min min time for the data
	 * @param timestamp_max max time for the data
	 * @param los list with learning objects to compute
	 * @param uniqueUser true or false (if true, counts a user only once)
	 * @param departments departments for the request
	 * @param degrees degrees for the request
	 * @param course courses for the request
	 * @return a list with the cumulative user access to the learning objects
	 * @throws SQLException
	 */
	@POST
	public HashMap<LearningObjects, HashMap<String, Integer>> compute(
			@FormParam("time_min") int timestamp_min,
			@FormParam("time_max") int timestamp_max,
			@FormParam("learningObjects") List<LearningObjects> los, 
			@FormParam("uniqueUser") boolean uniqueUser,
			@FormParam("departments") int[] departments,
			@FormParam("degrees") int[] degrees,
			@FormParam("course") int[] course) throws SQLException {
		
		//ergebnis
		HashMap<LearningObjects, HashMap<String, Integer>> result = new HashMap<LearningObjects, HashMap<String, Integer>>();
		//generiere querys
		HashMap<LearningObjects, String> querys = generateQuerys(timestamp_min, timestamp_max, los, departments, degrees, course);
		
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
		
		//Statement statement = connection.createStatement();
        Statement statement = session.connection().createStatement();
		Calendar cal = GregorianCalendar.getInstance();
		
		//SQL querys
		for(LearningObjects lo : querys.keySet()) {
			ResultSet set = statement.executeQuery(querys.get(lo));
			
			int week[] = new int[7];
			int hour[] = new int [24];
			HashSet<String> users = new HashSet<String>();
			
			//woche
			for(int i = 0; i < 7; i++) {week[i] =0;}
			//stunde
			for(int i = 0; i < 24; i++) {hour[i] =0;}

			//durchlaufen des result sets
			while(set.next()) {
				//nutzer nur einmal miteinrechnen
				if(uniqueUser) {
					String user = set.getString("user_id");
					if(users.contains(user)) {
						continue;
					}
					users.add(user);
				}
				long timestamp = set.getLong("timestamp");
				Date date = new Date(timestamp*1000);
				cal.setTime(date);
				//woche
				int day = cal.get(Calendar.DAY_OF_WEEK);
				//TAG 0 = montag
				switch(day) {
					case Calendar.MONDAY: week[0]++; break;
					case Calendar.TUESDAY: week[1]++; break;
					case Calendar.WEDNESDAY: week[2]++; break;
					case Calendar.THURSDAY: week[3]++; break;
					case Calendar.FRIDAY: week[4]++; break;
					case Calendar.SATURDAY: week[5]++; break;
					case Calendar.SUNDAY: week[6]++; break;
				}
				//stunde
				hour[cal.get(Calendar.HOUR_OF_DAY)]++;	
			}
			//zusammensetzen des results
			HashMap<String, Integer> cumulative = new HashMap<String, Integer>();
			cumulative.put("monday", new Integer(week[0]));
			cumulative.put("tuesday", new Integer(week[1]));
			cumulative.put("wednesday", new Integer(week[2]));
			cumulative.put("thursday", new Integer(week[3]));
			cumulative.put("friday", new Integer(week[4]));
			cumulative.put("saturday", new Integer(week[5]));
			cumulative.put("sunday", new Integer(week[6]));
			for(int i = 0; i < 24; i++) {
				cumulative.put(new Integer(i).toString(), hour[i]);
			}
			result.put(lo, cumulative);
		}
		return result;
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
	private HashMap<LearningObjects, String> generateQuerys(int timestamp_min, int timestamp_max, List<LearningObjects> los, int[] departments, int[] degrees, int[] course) {
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
			if(departments != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(" (");
				for(int i = 0; i < departments.length; i++) {
					sb.append(" department.id = "+departments[i]);
					if(i < departments.length -1) {
						sb.append(" OR");
					}
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			//filter degrees
			if(degrees != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(" (");
				for(int i = 0; i < degrees.length; i++) {
					sb.append(" degree.id = "+degrees[i]);
					if(i < degrees.length -1) {
						sb.append(" OR");
					}
				}
				sb.append(")");
				qa.add(sb.toString());
			}
			//filter course
			if(course != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				for(int i = 0; i < course.length; i++) {
					sb.append(" course.id = "+course[i]);
					if(i < course.length -1) {
						sb.append(" OR");
					}
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
