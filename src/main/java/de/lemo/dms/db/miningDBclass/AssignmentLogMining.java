package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;


/**This class represents the log table for the assignment modules.*/
public class AssignmentLogMining implements ILogMining, IMappingClass{

	private long id;
	private UserMining user;
	private CourseMining course;
	private AssignmentMining assignment;
	private Double grade;
	private Double finalgrade;
	private String action;
	private long timestamp;	
	private Long duration; 
	private Long platform;
	
	
	@Override
	public int compareTo(ILogMining arg0) {
		ILogMining s;
		try{
			s = arg0;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.timestamp > s.getTimestamp())
			return 1;
		if(this.timestamp < s.getTimestamp())
			return -1;
		return 0;
	}
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof AssignmentLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof AssignmentLogMining))
			return true;
		return false;
	}
	
	public String getTitle()
	{
		return this.assignment == null ? null : this.assignment.getTitle();
	}
	
	public Long getLearnObjId()
	{
		return this.assignment == null ? null : this.assignment.getId();
	}

	/** standard getter for the attribut finalgrade
	 * @return the final grade of the user in this quiz
	 */	
	public Double getFinalgrade() {
		return finalgrade;
	}
	/** standard setter for the attribut finalgrade
	 * @param finalgrade the final grade of the user in this quiz
	 */	
	public void setFinalgrade(Double finalgrade) {
		this.finalgrade = finalgrade;
	}	
	
	/** standard getter for the attribut id
	 * @return the identifier of the log entry
	 */	
	public long getId() {
		return id;
	}
	/** standard getter for the attribut id
	 * @param id the identifier of the log entry
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut user
	 * @return the user who interact with the quiz
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who interact with the quiz
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	
	/** parameterized setter for the attribut quiz
	 * @param user the id of the user who interact with the quiz
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addAssignment_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addAssignment_log(this);
		}
	}
	
	/** standard getter for the attribut course
	 * @return the course in wich the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course the course in wich the action takes place
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of the course in wich the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
       
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addAssignment_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addAssignment_log(this);
		}
	}
	/** standard getter for the attribut grade
	 * @return the grade in this case of action
	 */	
	public Double getGrade() {
		return grade;
	}
	/** standard setter for the attribut grade
	 * @param grade the grade in this case of action
	 */	
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	
	/** standard getter for the attribut timestamp
	 * @return the timestamp the action did occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribut timestamp
	 * @param timestamp the timestamp the action did occur
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/** standard getter for the attribut action
	 * @return the action which occur
	 */	
	public String getAction() {
		return action;
	}
	/** standard setter for the attribut action
	 * @param action the action which occur
	 */	
	public void setAction(String action) {
		this.action = action;
	}
	/** parameterized setter for the attribut assignment
	 * @param id the id of the assignment in which the action takes place
	 * @param assignmentMining a list of new added assignments, which is searched for the assignment with the id submitted in the parameter
	 * @param oldAssignmentMining a list of quiz in the miningdatabase, which is searched for the assignment with the id submitted in the parameter
	 */	
	public void setAssignment(long assignment, HashMap<Long, AssignmentMining> assignmentMining, HashMap<Long, AssignmentMining> oldAssignmentMining) {		
       
		if(assignmentMining.get(assignment) != null)
		{
			this.assignment = assignmentMining.get(assignment);
			assignmentMining.get(assignment).addAssignment_log(this);
		}
		if(this.assignment == null && oldAssignmentMining.get(assignment) != null)
		{
			this.assignment = oldAssignmentMining.get(assignment);
			oldAssignmentMining.get(assignment).addAssignment_log(this);
		}
	}
	/** standard setter for the attribut assignment
	 * @param assignment the assignment in which the action takes place
	 */		
	public void setAssignment(AssignmentMining assignment) {
		this.assignment = assignment;
	}
	
	/** standard getter for the attribut assignment
	 * @return the assignment in which the action takes place
	 */		
	public AssignmentMining getAssignment() {
		return assignment;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public Long getPrefix() {
		return 11L;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
