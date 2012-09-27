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
	
	/** The timecreated. */
	private long timecreated;
	
	/** The timemodified. */
	private long timemodified;
	
	/** The difficulty. */
	private String difficulty;
	
	/** The processing time. */
	private long processingTime;
	
	/** The url. */
	private String url;
	
	/** The position. */
	private long position;
	private Long platform;
	
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ResourceMining))
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
	
	/** The course_resource. */
	private Set<CourseResourceMining> course_resource = new HashSet<CourseResourceMining>();
	
	/** The resource_log. */
	private Set<ResourceLogMining> resource_log = new HashSet<ResourceLogMining>();

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ResourceMining)){
			return false;			
		}
		ResourceMining r = (ResourceMining)obj;
		if(r.id == this.id && r.timemodified == this.timemodified){
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
	 * standard getter for the attribut id.
	 *
	 * @return the identifier of the resource
	 */	
	public long getId() {
		return id;
	}
	
	/**
	 * standard setter for the attribut id.
	 *
	 * @param id the identifier of the resource
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * standard getter for the attribut type.
	 *
	 * @return the type of the resoure
	 */	
	public String getType() {
		return type;
	}
	
	/**
	 * standard setter for the attribut type.
	 *
	 * @param type the type of the resoure
	 */	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * standard getter for the attribut timecreated.
	 *
	 * @return the timestamp when the resource was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	
	/**
	 * standard setter for the attribut timecreated.
	 *
	 * @param timecreated the timestamp when the resource was created
	 */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	
	/**
	 * standard getter for the attribut timemodified.
	 *
	 * @return the timestamp when the resource was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	
	/**
	 * standard setter for the attribut timemodified.
	 *
	 * @param timemodified the timestamp when the resource was changed the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	
	/**
	 * standard setter for the attribut title.
	 *
	 * @param title the title of the resource
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * standard getter for the attribut title.
	 *
	 * @return the title of the resource
	 */	
	public String getTitle() {
		return title;
	}
	
	/**
	 * standard setter for the attribut course_resource.
	 *
	 * @param course_resource a set of entrys in the course_resource table which relate the resource to the courses
	 */	
	public void setCourse_resource(Set<CourseResourceMining> course_resource) {
		this.course_resource = course_resource;
	}
	
	/**
	 * standard getter for the attribut.
	 *
	 * @return a set of entrys in the course_resource table which relate the resource to the courses
	 */	
	public Set<CourseResourceMining> getCourse_resource() {
		return course_resource;
	}
	
	/**
	 * standard add method for the attribut course_resource.
	 *
	 * @param course_resource_add this entry will be added to the list of course_resource in this resource
	 */
	public void addCourse_resource(CourseResourceMining course_resource_add){	
		course_resource.add(course_resource_add);	
	}
	
	/**
	 * standard setter for the attribut resource_log.
	 *
	 * @param resource_log a set of entrys in the resource_log table which are related to this resource
	 */	
	public void setResource_log(Set<ResourceLogMining> resource_log) {
		this.resource_log = resource_log;
	}
	
	/**
	 * standard getter for the attribut resource_log.
	 *
	 * @return a set of entrys in the resource_log table which are related to this resource
	 */	
	public Set<ResourceLogMining> getResource_log() {
		return resource_log;
	}
	
	/**
	 * standard add method for the attribut resource_log.
	 *
	 * @param resource_log_add this entry will be added to the list of resource_log in this resource
	 */
	public void addResource_log(ResourceLogMining resource_log_add){	
		resource_log.add(resource_log_add);	
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
