package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the log table for the scorm modules.*/
public class ScormLogMining implements ILogMining , IMappingClass{

	private long id;
	private UserMining user;
	private CourseMining course;
	private ScormMining scorm;
	private double grade;
	private double finalgrade;
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
		if(o == null || !(o instanceof ScormLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ScormLogMining))
			return true;
		return false;
	}
	
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getTitle()
	{
		return this.scorm == null ? null : this.scorm.getTitle();
	}
	
	public Long getLearnObjId()
	{
		return this.scorm == null ? null : this.scorm.getId();

	}
	
	/** standard getter for the attribute finalgrade
	 * @return the final grade of the user in this quiz
	 */	
	public double getFinalgrade() {
		return finalgrade;
	}
	/** standard setter for the attribute finalgrade
	 * @param finalgrade the final grade of the user in this quiz
	 */	
	public void setFinalgrade(double finalgrade) {
		this.finalgrade = finalgrade;
	}	

	/** standard getter for the attribute id
	 * @return the identifier of the log entry
	 */	
	public long getId() {
		return id;
	}
	/** standard getter for the attribute id
	 * @param id the identifier of the log entry
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribute user
	 * @return the user who interact with the quiz
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribute user
	 * @param user the user who interact with the quiz
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribute quiz
	 * @param user the id of the user who interact with the quiz
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the mining database, which is searched for the user with the id submitted in the user parameter
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
	/** standard getter for the attribute course
	 * @return the course in which the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribute course
	 * @param course the course in which the action takes place
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribute course
	 * @param course the id of the course in which the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the mining database, which is searched for the course with the id submitted in the course parameter
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
	/** standard getter for the attribute grade
	 * @return the grade in this case of action
	 */	
	public double getGrade() {
		return grade;
	}
	/** standard setter for the attribute grade
	 * @param grade the grade in this case of action
	 */	
	public void setGrade(double grade) {
		this.grade = grade;
	}
	/** standard getter for the attribute timestamp
	 * @return the timestamp the action did occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribute timestamp
	 * @param timestamp the timestamp the action did occur
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/** standard getter for the attribute action
	 * @return the action which occur
	 */	
	public String getAction() {
		return action;
	}
	/** standard setter for the attribute action
	 * @param action the action which occur
	 */	
	public void setAction(String action) {
		this.action = action;
	}
	/** parameterized setter for the attribute
	 * @param id the id of the scorm package in which the action takes place
	 * @param scormMining a list of new added scorm packages, which is searched for the scorm package with the id submitted in the parameter
	 * @param oldScormMining a list of scorm packages in the mining database, which is searched for the scorm package with the id submitted in the parameter
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
	/** standard setter for the attribute scorm
	 * @param scorm the scorm package in which the action takes place
	 */		
	public void setScorm(ScormMining scorm) {
		this.scorm = scorm;
	}
	/** standard getter for the attribute scorm
	 * @return the scorm package in which the action takes place
	 */		
	public ScormMining getScorm() {
		return scorm;
	}

	@Override
	public Long getPrefix() {
		return 17L;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
