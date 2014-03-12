/**
 * File ./src/main/java/de/lemo/dms/db/mapping/CourseResource.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseResource.java
 * Date 2014-02-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMapping;

/** This class represents the relationship between courses and resources. */
@Entity
@Table(name = "lemo_course_collaborative_object")
public class CourseCollaborativeObject implements IMapping, ICourseLORelation {
	
	private long id;
	private Course course;
	private CollaborativeObject collaborativeObject;
	
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof CourseCollaborativeObject)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseCollaborativeObject)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * @return the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public Course getCourse() {
		return course;
	}


	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public void setCourse(final long course, final Map<Long, Course> courses,
			final Map<Long, Course> oldCourses) {
		if (courses.get(course) != null)
		{
			this.course = courses.get(course);
			courses.get(course).addCourseCollaborativeObject(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addCourseCollaborativeObject(this);
		}
	}
	
	public void setCollaborativeObject(final long collaborativeObject, final Map<Long, CollaborativeObject> collaborativeObjects,
			final Map<Long, CollaborativeObject> oldCollaborativeObjects) {
		if (collaborativeObjects.get(collaborativeObject) != null)
		{
			this.collaborativeObject = collaborativeObjects.get(collaborativeObject);
			collaborativeObjects.get(collaborativeObject).addCourseCollaborativeObject(this);
		}
		if ((this.collaborativeObject == null) && (oldCollaborativeObjects.get(collaborativeObject) != null))
		{
			this.collaborativeObject = oldCollaborativeObjects.get(collaborativeObject);
			oldCollaborativeObjects.get(collaborativeObject).addCourseCollaborativeObject(this);
		}
	}


	/**
	 * @return the resource
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="collaborative_object_id")
	public CollaborativeObject getCollaborativeObject() {
		return collaborativeObject;
	}


	/**
	 * @param learningObject the resource to set
	 */
	public void setCollaborativeObject(CollaborativeObject collaborativeObject) {
		this.collaborativeObject = collaborativeObject;
	}


	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	@Transient
	public ILearningObject getLearningObj() {
		return this.getCollaborativeObject();
	}

	@Override
	@Transient
	public String getType() {
		return "COLLABORATIVEOBJECT";
	}
}
