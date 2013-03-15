/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ResourceMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table resource. */
public class ResourceMining implements IMappingClass, ILearningObject {

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
	
	private static final Long PREFIX = 16L;
	
	public Long getPrefix()
	{
		return PREFIX;
	}

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof ResourceMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ResourceMining)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public long getPosition() {
		return this.position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param position
	 *            the new position
	 */
	public void setPosition(final long position) {
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof ResourceMining)) {
			return false;
		}
		final ResourceMining r = (ResourceMining) obj;
		if ((r.id == this.id) && (r.timeModified == this.timeModified)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * Gets the estimated difficulty of the resource.
	 * 
	 * @return The estimated difficulty of the resource.
	 */
	public String getDifficulty() {
		return this.difficulty;
	}

	/**
	 * Sets the estimated difficulty of the resource.
	 * 
	 * @param difficulty
	 *            The estimated difficulty of the resource.
	 */
	public void setDifficulty(final String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Gets the recommended processing time of the resource.
	 * 
	 * @return The recommended processing time of the resource.
	 */
	public long getProcessingTime() {
		return this.processingTime;
	}

	/**
	 * Sets the recommended processing time of the resource.
	 * 
	 * @param processingTime
	 *            the recommended processing time of the resource.
	 */
	public void setProcessingTime(final long processingTime) {
		this.processingTime = processingTime;
	}

	/**
	 * standard getter for the attribute id.
	 * 
	 * @return the identifier of the resource
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id.
	 * 
	 * @param id
	 *            the identifier of the resource
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute type.
	 * 
	 * @return the type of the resource
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute type.
	 * 
	 * @param type
	 *            the type of the resource
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute timeCreated.
	 * 
	 * @return the timestamp when the resource was created
	 */
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timeCreated.
	 * 
	 * @param timeCreated
	 *            the timestamp when the resource was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timeModified.
	 * 
	 * @return the timestamp when the resource was changed the last time
	 */
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timeModified.
	 * 
	 * @param timeModified
	 *            the timestamp when the resource was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute title.
	 * 
	 * @param title
	 *            the title of the resource
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title.
	 * 
	 * @return the title of the resource
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseResources
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setCourseResources(final Set<CourseResourceMining> courseResources) {
		this.courseResources = courseResources;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	public Set<CourseResourceMining> getCourseResources() {
		return this.courseResources;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseResource
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseResource(final CourseResourceMining courseResource) {
		this.courseResources.add(courseResource);
	}

	/**
	 * standard setter for the attribute resource_log.
	 * 
	 * @param resourceLogs
	 *            a set of entries in the resource_log table which are related to this resource
	 */
	public void setResourceLogs(final Set<ResourceLogMining> resourceLogs) {
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
	 * @param resourceLog
	 *            this entry will be added to the list of resource_log in this resource
	 */
	public void addResourceLog(final ResourceLogMining resourceLog) {
		this.resourceLogs.add(resourceLog);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
