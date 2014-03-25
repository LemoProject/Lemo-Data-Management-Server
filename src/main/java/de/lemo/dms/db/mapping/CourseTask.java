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
import de.lemo.dms.db.mapping.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMapping;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

/** This class represents the relationship between courses and resources. */
@Entity
@Table(name = "lemo_course_task")
public class CourseTask implements IMapping, ICourseLORelation, ICourseRatedObjectAssociation {
	
	private long id;
	private Course course;
	private Task task;
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof CourseTask)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseTask)) {
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
			courses.get(course).addCourseTask(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addCourseTask(this);
		}
	}


	/**
	 * @return the task
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	public Task getTask() {
		return task;
	}


	/**
	 * @param Task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	
	public void setTask(final long task, final Map<Long, Task> tasks,
			final Map<Long, Task> oldTasks) {
		if (tasks.get(task) != null)
		{
			this.task = tasks.get(task);
			tasks.get(task).addCourseTask(this);
		}
		if ((this.task == null) && (oldTasks.get(task) != null))
		{
			this.task = oldTasks.get(task);
			oldTasks.get(task).addCourseTask(this);
		}
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
		
		return this.task;
	}
	
	@Override
	@Transient
	public String getType() {
		return "TASK";
	}

	@Override
	@Transient
	public IRatedObject getRatedObject() {
		return this.getTask();
	}

}
