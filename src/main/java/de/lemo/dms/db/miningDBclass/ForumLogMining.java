package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;


/**This class represents the log table for the forum object.*/
public class ForumLogMining implements ILogMining , IMappingClass {
	
	private long id;
	private ForumMining forum;
	private	UserMining user;
	private CourseMining course;
	private String action;
	private String subject;
	private String message;
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
		if(o == null || !(o instanceof ForumLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ForumLogMining))
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
		return this.forum.getTitle();
	}

	public Long getLearnObjId()
	{
		return this.forum == null ? null : this.forum.getId();
	}
	
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
	/** standard getter for the attribut subject
	 * @return the subject of the entry
	 */	
	public String getSubject() {
		return subject;
	}
	/** standard setter for the attribut subject
	 * @param subject the subject of the entry
	 */	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/** standard getter for the attribut message
	 * @return the message of the entry
	 */	
	public String getMessage() {
		return message;
	}
	/** standard setter for the attribut message
	 * @param message the message of the entry
	 */	
	public void setMessage(String message) {
		this.message = message;
	}
	/** standard getter for the attribut timestamp
	 * @return the time when the logged action occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribut timestamp
	 * @param timestamp the time when the logged action occur
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
	/** parameterized setter for the attribut course
	 * @param course the id of the course in which the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
        
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addForum_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addForum_log(this);
		}
	}
	/** standard setter for the attribut course
	 * @param course the course in which the action takes place
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut forum
	 * @param forum the id of the forum with which was interacted
	 * @param forumMining a list of new added forum, which is searched for the forum with the id submitted in the forum parameter
	 * @param oldForumMining a list of forum in the miningdatabase, which is searched for the forum with the id submitted in the forum parameter
	 */	
	public void setForum(long forum, HashMap<Long, ForumMining> forumMining, HashMap<Long, ForumMining> oldForumMining) {		
        
		if(forumMining.get(forum) != null)
		{
			this.forum = forumMining.get(forum);
			forumMining.get(forum).addForum_log(this);
		}
		if(this.forum == null && oldForumMining.get(forum) != null)
		{
			this.forum = oldForumMining.get(forum);
			oldForumMining.get(forum).addForum_log(this);
		}
	}
	
	/** standard getter for the attribut forum
	 * @return the forum with which was interacted
	 */		
	public ForumMining getForum() {
		return forum;
	}
	/** standard setter for the attribut forum
	 * @param forum the forum with which was interacted
	 */	
	public void setForum(ForumMining forum) {
		this.forum = forum;
	}
	/** standard setter for the attribut user
	 * @param user the user who interact with the forum
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** standard getter for the attribut user
	 * @return the user who interact with the forum
	 */	
	public UserMining getUser() {
		return user;
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
			userMining.get(user).addForum_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addForum_log(this);
		}
	}

	@Override
	public Long getPrefix() {
		return 15L;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
