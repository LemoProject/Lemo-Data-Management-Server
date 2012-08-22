package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;


/**This class represents the log table for the resource object.*/
public class ResourceLogMining implements Comparable<ILogMining>, ILogMining , IMappingClass{	
	
	private long id;
	private ResourceMining resource;
	private	UserMining user;
	private CourseMining course;
	private String action;
	private long timestamp;
	private long duration;
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ResourceLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ResourceLogMining))
			return true;
		return false;
	}
	
	public String getTitle()
	{
		return this.resource.getTitle();
	}
	
	public Long getLearnObjId()
	{
		return this.resource == null ? null : this.resource.getId();
	}
	
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
	
	/**
	 * Gets the duration of the access.
	 * @return The duration of the access.
	 */
	public Long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
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
	/** standard setter for the attribut course
	 * @param course the course in which the action takes place
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
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
			courseMining.get(course).addResource_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addResource_log(this);
		}
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
	/** standard setter for the attribut resource
	 * @param resource the resource with which was interacted
	 */	
	public void setResource(ResourceMining resource) {
		this.resource = resource;
	}
	/** standard getter for the attribut resource
	 * @return the resource with which was interacted
	 */	
	public ResourceMining getResource() {
		return resource;
	}
	/** parameterized setter for the attribut resource
	 * @param resource the id of the resource with which was interacted
	 * @param resourceMining a list of new added resources, which is searched for the resource with the id submitted in the resource parameter
	 * @param oldResourceMining a list of resource in the miningdatabase, which is searched for the resource with the id submitted in the resource parameter
	 */		
	public void setResource(long resource, HashMap<Long, ResourceMining> resourceMining, HashMap<Long, ResourceMining> oldResourceMining) {		
		if(resourceMining.get(resource) != null)
		{
			this.resource = resourceMining.get(resource);
			resourceMining.get(resource).addResource_log(this);
		}
		if(this.resource == null && oldResourceMining.get(resource) != null)
		{
			this.resource = oldResourceMining.get(resource);
			oldResourceMining.get(resource).addResource_log(this);
		}
	}
	/** standard setter for the attribut user
	 * @param user the user who interact with the resource
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** standard getter for the attribut user
	 * @return the user who interact with the resource
	 */	
	public UserMining getUser() {
		return user;
	}
	/** parameterized setter for the attribut user
	 * @param user the id of the user who interacts with the resource
	 * @param userMining a list of newly added users, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of users in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */		
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {			
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addResource_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addResource_log(this);
		}
	}

	@Override
	public Long getPrefix() {
		return 1006L;
	}
}
