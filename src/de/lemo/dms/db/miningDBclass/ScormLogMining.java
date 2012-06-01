package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

/**This class represents the log table for the scorm modules.*/
public class ScormLogMining{

	private long id;
	private UserMining user;
	private CourseMining course;
	private ScormMining scorm;
	private double grade;
	private double finalgrade;
	private String action;
	private long timestamp;

	/** standard getter for the attribut finalgrade
	 * @return the final grade of the user in this quiz
	 */	
	public double getFinalgrade() {
		return finalgrade;
	}
	/** standard setter for the attribut finalgrade
	 * @param finalgrade the final grade of the user in this quiz
	 */	
	public void setFinalgrade(double finalgrade) {
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
			userMining.get(user).addScorm_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addScorm_log(this);
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
			courseMining.get(course).addScorm_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addScorm_log(this);
		}
	}
	/** standard getter for the attribut grade
	 * @return the grade in this case of action
	 */	
	public double getGrade() {
		return grade;
	}
	/** standard setter for the attribut grade
	 * @param grade the grade in this case of action
	 */	
	public void setGrade(double grade) {
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
	/** parameterized setter for the attribut
	 * @param id the id of the scorm package in which the action takes place
	 * @param scormMining a list of new added scorm packages, which is searched for the scorm package with the id submitted in the parameter
	 * @param oldScormMining a list of scorm packages in the miningdatabase, which is searched for the scorm package with the id submitted in the parameter
	 */	
	public void setScorm(long scorm, HashMap<Long, ScormMining> scormMining, HashMap<Long, ScormMining> oldScormMining) {		
		
		if(scormMining.get(scorm) != null)
		{
			this.scorm = scormMining.get(scorm);
			scormMining.get(scorm).addScorm_log(this);
		}
		if(this.scorm == null && oldScormMining.get(scorm) != null)
		{
			this.scorm = oldScormMining.get(scorm);
			oldScormMining.get(scorm).addScorm_log(this);
		}
	}
	/** standard setter for the attribut scorm
	 * @param scorm the scorm package in which the action takes place
	 */		
	public void setScorm(ScormMining scorm) {
		this.scorm = scorm;
	}
	/** standard getter for the attribut scorm
	 * @return the scorm package in which the action takes place
	 */		
	public ScormMining getScorm() {
		return scorm;
	}
}
