/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/AssignGradesLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;
/**
 * Mapping class for table Assign.
 * 
 * @author S.Schwarzrock
 *
 */
public class AssignGradesLMS {

	private long id;
	private long assignment;
	private long user;
	private Double grade;
	private long grader;
	private long timemodified;
	private long timecreated;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	public long getAssignment() {
		return assignment;
	}

	
	public void setAssignment(long assignment) {
		this.assignment = assignment;
	}

	
	public long getUser() {
		return user;
	}

	
	public void setUser(long user) {
		this.user = user;
	}

	public Double getGrade() {
		return grade;
	}
	
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	
	public long getTimemodified() {
		return timemodified;
	}
	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	
	public long getTimecreated() {
		return timecreated;
	}
	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}

	public long getGrader() {
		return grader;
	}

	public void setGrader(long grader) {
		this.grader = grader;
	}


}
