package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/**This class represents the table assignment.*/
public class AssignmentMining implements IMappingClass, ILearningObject, IRatedObject{

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;	
	private long timeCreated;
	private long timeModified;
	private Long platform;
	
	private Set<AssignmentLogMining> assignmentLogs = new HashSet<AssignmentLogMining>();
	private Set<CourseAssignmentMining> courseAssignments = new HashSet<CourseAssignmentMining>();

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof AssignmentMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof AssignmentMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute timestamp
	 * @return the timestamp the assignment will be accessible after by students
	 */
	public long getTimeOpen() {
		return timeOpen;
	}
	/** standard setter for the attribute timestamp
	 * @param timeOpen the timestamp the assignment will be accessible after by students
	 * */		
	public void setTimeopen(long timeOpen) {
		this.timeOpen = timeOpen;
	}
	/** standard getter for the attribute timeclose
	 * @return the timestamp after that the assignment will be not accessible any more by students
	 */	
	public long getTimeClose() {
		return timeClose;
	}
	/** standard setter for the attribute timeclose
	 * @param timeClose the timestamp after that the assignment will be not accessible any more by students
	 * */		
	public void setTimeClose(long timeClose) {
		this.timeClose = timeClose;
	}
	/** standard getter for the attribute timecreated
	 * @return the timestamp when the assignment was created
	 */	
	public long getTimeCreated() {
		return timeCreated;
	}
	/** standard setter for the attribute timecreated
	 * @param timeCreated the timestamp when the assignment was created
	 * */	
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	/** standard getter for the attribute timemodified
	 * @return the timestamp when the assignment was changed the last time
	 */	
	public long getTimeModified() {
		return timeModified;
	}
	/** standard setter for the attribute timemodified
	 * @param timeModified the timestamp when the assignment was changed the last time
	 * */	
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
	/** standard setter for the attribute title
	 * @param title the title of the assignment
	 * */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribute title
	 * @return the title of the assignment
	 */	
	public String getTitle() {
		return title;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier of the assignment
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier of the assignment
	 */		
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribute maxgrade
	 * @return the maximum grade which is set for the assignment
	 */
	public Double getMaxGrade() {
		return maxGrade;
	}
	/** standard setter for the attribute maxgrade
	 * @param maxGrade the maximum grade which is set for the assignment
	 * */
	public void setMaxgrade(double maxGrade) {
		this.maxGrade = maxGrade;
	}
	/** standard setter for the attribute type
	 * @param type the type of this assignment
	 * */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribute type
	 * @return the type of this assignment
	 * */
	public String getType() {
		return type;
	}
	/** standard setter for the attribute assignment_log
	 * @param assignmentLog a set of entries in the assignment_log table which are related with this assignment
	 * */	
	public void setAssignmentLogs(Set<AssignmentLogMining> assignmentLog) {
		this.assignmentLogs = assignmentLog;
	}
	/** standard getter for the attribute assignment_log
	 * @return a set of entries in the quiz_log table which are related with this assignment
	 */	
	public Set<AssignmentLogMining> getAssignmentLogs() {
		return assignmentLogs;
	}
	/** standard setter for the attribute assignment_log
	 * @param assignmentLog this entry will be added to the list of assignment_log in this assignment
	 * */	
	public void addAssignmentLog(AssignmentLogMining assignmentLog){	
		this.assignmentLogs.add(assignmentLog);
	}
	/** standard setter for the attribute course_assignment
	 * @param courseAssignment a set of entries in the course_assignment table which are related with this assignment
	 * */	
	public void setCourseAssignments(Set<CourseAssignmentMining> courseAssignment) {
		this.courseAssignments = courseAssignment;
	}
	/** standard getter for the attribute course_assignment
	 * @return a set of entries in the course_assignment table which are related with this assignment
	 */	
	public Set<CourseAssignmentMining> getCourseAssignments() {
		return courseAssignments;
	}
	/** standard setter for the attribute course_assignment
	 * @param courseAssignment this entry will be added to the list of course_assignment in this assignment
	 * */	
	public void addCourseAssignment(CourseAssignmentMining courseAssignment){	
		this.courseAssignments.add(courseAssignment);
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	@Override
	public Long getPrefix() {
		return 11L;
	}
}