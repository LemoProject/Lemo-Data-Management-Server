/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseResourceMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseLORelation;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the relationship between courses and resources. */
public class CourseResourceMining implements IMappingClass, ICourseLORelation {

	private long id;
	private CourseMining course;
	private ResourceMining resource;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseResourceMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseResourceMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and resource
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and resource
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a course in which the resource is used
	 */
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the resource is used
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribute
	 * 
	 * @param course
	 *            the id of a course in which the resource is used
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
			courseMining.get(course).addCourseResource(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseResource(this);
		}
	}

	/**
	 * standard getter for the attribute resource
	 * 
	 * @return the resource which is used in the course
	 */
	public ResourceMining getResource() {
		return this.resource;
	}

	/**
	 * standard setter for the attribute resource
	 * 
	 * @param resource
	 *            the resource which is used in the course
	 */
	public void setResource(final ResourceMining resource) {
		this.resource = resource;
	}

	/**
	 * parameterized setter for the attribute resource
	 * 
	 * @param resource
	 *            the id of the resource which is used in the course
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
			resourceMining.get(resource).addCourseResource(this);
		}
		if ((this.resource == null) && (oldResourceMining.get(resource) != null))
		{
			this.resource = oldResourceMining.get(resource);
			oldResourceMining.get(resource).addCourseResource(this);
		}
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	public Long getCourseId() {
		return this.course.getId();
	}

	@Override
	public Long getLearningObjectId() {
		return this.resource.getId();
	}
}
