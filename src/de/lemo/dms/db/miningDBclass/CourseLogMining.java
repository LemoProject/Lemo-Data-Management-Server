package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

/**This class represents the log table for the course object.*/
public class CourseLogMining {

	private long id;
	private CourseMining course;
	private	UserMining user;
	private String action;
	private long timestamp;

	/** standard getter for the attribut id
	 * @return the identifier of the log entry
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the log entry
	 */	
	public void setId(long id) {
		this.id = id;
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
	/** standard getter for the attribut course
	 * @return the course in which the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course the course in which the action takes place
	 */		
	public void setCourse(CourseMining course) {
		this.course = course;
	}	
	/** parameterized setter for the attribut course
	 * @param course the id of the course in which the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_updates(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_updates(this);
		}
	}
	/** standard getter for the attribut user
	 * @return the user who interact with the course
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who interact with the course
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribut user
	 * @param user the id of the user who interact with the resource
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {			
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addCourse_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addCourse_log(this);
		}
	}
}
