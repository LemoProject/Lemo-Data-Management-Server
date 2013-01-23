package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;


// TODO: Auto-generated Javadoc
/**This class represents the table resource.*/
public class ResourceMining implements IMappingClass, ILearningObject{

	/** The id. */
	private long id;
	/** The type. */
	private String type;
	/** The title. */
	private String title;
	/** The timeCreated. */
	private long timeCreated;
	/** The timeModified. */
	private long timeModified;
	/** The difficulty. */
	private String difficulty;
	/** The processing time. */
	private long processingTime;
	/** The url. */
	private String url;	
	/** The position. */
	private long position;
	private Long platform;
	/** The course_resource. */
	private Set<CourseResourceMining> courseResources = new HashSet<CourseResourceMining>();
	/** The resource_log. */
	private Set<ResourceLogMining> resourceLogs = new HashSet<ResourceLogMining>();
	
	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof ResourceMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ResourceMining))
			return true;
		return false;
	}
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public long getPosition() {
		return position;
	}


	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(long position) {
		this.position = position;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ResourceMining)){
			return false;			
		}
		ResourceMining r = (ResourceMining)obj;
		if(r.id == this.id && r.timeModified == this.timeModified){
			return true;
		}		
		return false;
	}	
	

	/**
	 * Gets the estimated difficulty of the resource.
	 * @return The estimated difficulty of the resource.
	 */
	public String getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets the estimated difficulty of the resource.
	 * @param difficulty The estimated difficulty of the resource.
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Gets the recommended processing time of the resource.
	 * @return The recommended processing time of the resource.
	 */
	public long getProcessingTime() {
		return processingTime;
	}

	/**
	 * Sets the recommended processing time of the resource.
	 * @param processingTime the recommended processing time of the resource.
	 */
	public void setProcessingTime(long processingTime) {
		this.processingTime = processingTime;
	}

	
	/**
	 * standard getter for the attribute id.
	 *
	 * @return the identifier of the resource
	 */	
	public long getId() {
		return id;
	}
	
	/**
	 * standard setter for the attribute id.
	 *
	 * @param id the identifier of the resource
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * standard getter for the attribute type.
	 *
	 * @return the type of the resource
	 */	
	public String getType() {
		return type;
	}
	
	/**
	 * standard setter for the attribute type.
	 *
	 * @param type the type of the resource
	 */	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * standard getter for the attribute timeCreated.
	 *
	 * @return the timestamp when the resource was created
	 */	
	public long getTimeCreated() {
		return timeCreated;
	}
	
	/**
	 * standard setter for the attribute timeCreated.
	 *
	 * @param timeCreated the timestamp when the resource was created
	 */	
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	
	/**
	 * standard getter for the attribute timeModified.
	 *
	 * @return the timestamp when the resource was changed the last time
	 */	
	public long getTimeModified() {
		return timeModified;
	}
	
	/**
	 * standard setter for the attribute timeModified.
	 *
	 * @param timeModified the timestamp when the resource was changed the last time
	 */	
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
	
	/**
	 * standard setter for the attribute title.
	 *
	 * @param title the title of the resource
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * standard getter for the attribute title.
	 *
	 * @return the title of the resource
	 */	
	public String getTitle() {
		return title;
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 *
	 * @param courseResources a set of entries in the course_resource table which relate the resource to the courses
	 */	
	public void setCourseResources(Set<CourseResourceMining> courseResources) {
		this.courseResources = courseResources;
	}
	
	/**
	 * standard getter for the attribute.
	 *
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */	
	public Set<CourseResourceMining> getCourseResources() {
		return courseResources;
	}
	
	/**
	 * standard add method for the attribute course_resource.
	 *
	 * @param courseResource this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseResource(CourseResourceMining courseResource){	
		this.courseResources.add(courseResource);	
	}
	
	/**
	 * standard setter for the attribute resource_log.
	 *
	 * @param resourceLogs a set of entries in the resource_log table which are related to this resource
	 */	
	public void setResourceLogs(Set<ResourceLogMining> resourceLogs) {
		this.resourceLogs = resourceLogs;
	}
	
	/**
	 * standard getter for the attribute resource_log.
	 *
	 * @return a set of entries in the resource_log table which are related to this resource
	 */	
	public Set<ResourceLogMining> getResourceLogs() {
		return this.resourceLogs;
	}
	
	/**
	 * standard add method for the attribute resource_log.
	 *
	 * @param resourceLog this entry will be added to the list of resource_log in this resource
	 */
	public void addResourceLog(ResourceLogMining resourceLog){	
		this.resourceLogs.add(resourceLog);	
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
