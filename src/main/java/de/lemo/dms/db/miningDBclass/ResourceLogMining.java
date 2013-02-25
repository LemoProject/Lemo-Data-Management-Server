/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ResourceLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the log table for the resource object. */
public class ResourceLogMining implements Comparable<ILogMining>, ILogMining, IMappingClass {

	private long id;
	private ResourceMining resource;
	private UserMining user;
	private CourseMining course;
	private String action;
	private long timestamp;
	private Long duration;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof ResourceLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ResourceLogMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	@Override
	public String getTitle()
	{
		return this.resource == null ? null : this.resource.getTitle();
	}

	@Override
	public Long getLearnObjId()
	{
		return this.resource == null ? null : this.resource.getId();
	}

	@Override
	public int compareTo(final ILogMining arg0) {
		ILogMining s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (this.timestamp > s.getTimestamp()) {
			return 1;
		}
		if (this.timestamp < s.getTimestamp()) {
			return -1;
		}
		return 0;
	}

	/**
	 * Gets the duration of the access.
	 * 
	 * @return The duration of the access.
	 */
	@Override
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the log entry
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the log entry
	 */
	@Override
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the action did occur
	 */
	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp the action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of the course in which the action takes place
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addResourceLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addResourceLog(this);
		}
	}

	/**
	 * standard getter for the attribute action
	 * 
	 * @return the action which occur
	 */
	@Override
	public String getAction() {
		return this.action;
	}

	/**
	 * standard setter for the attribute action
	 * 
	 * @param action
	 *            the action which occur
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * standard setter for the attribute resource
	 * 
	 * @param resource
	 *            the resource with which was interacted
	 */
	public void setResource(final ResourceMining resource) {
		this.resource = resource;
	}

	/**
	 * standard getter for the attribute resource
	 * 
	 * @return the resource with which was interacted
	 */
	public ResourceMining getResource() {
		return this.resource;
	}

	/**
	 * parameterized setter for the attribute resource
	 * 
	 * @param resource
	 *            the id of the resource with which was interacted
	 * @param resourceMining
	 *            a list of new added resources, which is searched for the resource with the id submitted in the
	 *            resource parameter
	 * @param oldResourceMining
	 *            a list of resource in the miningdatabase, which is searched for the resource with the id submitted in
	 *            the resource parameter
	 */
	public void setResource(final long resource, final Map<Long, ResourceMining> resourceMining,
			final Map<Long, ResourceMining> oldResourceMining) {
		if (resourceMining.get(resource) != null)
		{
			this.resource = resourceMining.get(resource);
			resourceMining.get(resource).addResourceLog(this);
		}
		if ((this.resource == null) && (oldResourceMining.get(resource) != null))
		{
			this.resource = oldResourceMining.get(resource);
			oldResourceMining.get(resource).addResourceLog(this);
		}
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the resource
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the resource
	 */
	@Override
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the id of the user who interacts with the resource
	 * @param userMining
	 *            a list of newly added users, which is searched for the user with the id submitted in the user
	 *            parameter
	 * @param oldUserMining
	 *            a list of users in the miningdatabase, which is searched for the user with the id submitted in the
	 *            user parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addResourceLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addResourceLog(this);
		}
	}

	@Override
	public Long getPrefix() {
		return 16L;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
