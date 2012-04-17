package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**This class represents the table assignment.*/
public class AssignmentMining {

	private long id;
	private String type;
	private String title;
	private double maxgrade;
	private long timeopen;
	private long timeclose;	
	private long timecreated;
	private long timemodified;
	
	private Set<AssignmentLogMining> assignment_log = new HashSet<AssignmentLogMining>();
	private Set<CourseAssignmentMining> course_assignment = new HashSet<CourseAssignmentMining>();

	/** standard getter for the attribut timestamp
	 * @return the timestamp the assignment will be accessable after by students
	 */
	public long getTimeopen() {
		return timeopen;
	}
	/** standard setter for the attribut timestamp
	 * @param timeopen the timestamp the assignment will be accessable after by students
	 * */		
	public void setTimeopen(long timeopen) {
		this.timeopen = timeopen;
	}
	/** standard getter for the attribut timeclose
	 * @return the timestamp after that the assignment will be not accessable any more by students
	 */	
	public long getTimeclose() {
		return timeclose;
	}
	/** standard setter for the attribut timeclose
	 * @param timeclose the timestamp after that the assignment will be not accessable any more by students
	 * */		
	public void setTimeclose(long timeclose) {
		this.timeclose = timeclose;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the assignment was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the assignment was created
	 * */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the assignment was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the assignment was changed the last time
	 * */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribut title
	 * @param title the title of the assignment
	 * */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut title
	 * @return the title of the assignment
	 */	
	public String getTitle() {
		return title;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier of the assignment
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the assignment
	 */		
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut maxgrade
	 * @return the maximum grade which is set for the assignment
	 */
	public double getMaxgrade() {
		return maxgrade;
	}
	/** standard setter for the attribut maxgrade
	 * @param maxgrade the maximum grade which is set for the assignment
	 * */
	public void setMaxgrade(double maxgrade) {
		this.maxgrade = maxgrade;
	}
	/** standard setter for the attribut type
	 * @param type the type of this assignment
	 * */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribut type
	 * @return the type of this assignment
	 * */
	public String getType() {
		return type;
	}
	/** standard setter for the attribut assignment_log
	 * @param assignment_log a set of entrys in the assignment_log table which are related with this assignment
	 * */	
	public void setAssignment_log(Set<AssignmentLogMining> assignment_log) {
		this.assignment_log = assignment_log;
	}
	/** standard getter for the attribut assignment_log
	 * @return a set of entrys in the quiz_log table which are related with this assignment
	 */	
	public Set<AssignmentLogMining> getAssignment_log() {
		return assignment_log;
	}
	/** standard setter for the attribut assignment_log
	 * @param assignment_log_add this entry will be added to the list of assignment_log in this assignment
	 * */	
	public void addAssignment_log(AssignmentLogMining assignment_log_add){	
		assignment_log.add(assignment_log_add);
	}
	/** standard setter for the attribut course_assignment
	 * @param course_assignment a set of entrys in the course_assignment table which are related with this assignment
	 * */	
	public void setCourse_assignment(Set<CourseAssignmentMining> course_assignment) {
		this.course_assignment = course_assignment;
	}
	/** standard getter for the attribut course_assignment
	 * @return a set of entrys in the course_assignment table which are related with this assignment
	 */	
	public Set<CourseAssignmentMining> getCourse_assignment() {
		return course_assignment;
	}
	/** standard setter for the attribut course_assignment
	 * @param course_assignment_add this entry will be added to the list of course_assignment in this assignment
	 * */	
	public void addCourse_assignment(CourseAssignmentMining course_assignment_add){	
		course_assignment.add(course_assignment_add);
	}
}