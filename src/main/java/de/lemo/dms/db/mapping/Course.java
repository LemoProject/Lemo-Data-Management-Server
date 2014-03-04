/**
 * File ./src/main/java/de/lemo/dms/db/mapping/Course.java
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
 * File ./main/java/de/lemo/dms/db/mapping/Course.java
 * Date 2014-02-04
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

/** This class represents the table course. */
@Entity
@Table(name = "lemo_course")
public class Course implements IMapping{
	
	private long id;
	private Platform platform;
	private String title;
	private long timeModified;
	
	private Set<CourseLearningObject> courseResources = new HashSet<CourseLearningObject>();
	private Set<CourseTask> courseTasks = new HashSet<CourseTask>();
	private Set<ViewLog> eventLogs = new HashSet<ViewLog>();
	private Set<SubmissionLog> taskLogs = new HashSet<SubmissionLog>();
	private Set<CollaborativeLog> collaborativeLogs = new HashSet<CollaborativeLog>();
	private Set<TaskUser> taskUsers = new HashSet<TaskUser>();
	private Set<CourseUser> courseUsers = new HashSet<CourseUser>();
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof Course)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Course)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
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

	/**
	 * @return the title
	 */
	@Lob
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the timeModified
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return timeModified;
	}

	/**
	 * @param timeModified the timeModified to set
	 */
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}


	
	/**
	 * standard setter for the attribute course_resource
	 * 
	 * @param courseResource
	 *            a set of entries in the course_resource table which shows the resources in this course
	 */
	public void setCourseResources(final Set<CourseLearningObject> courseResource) {
		this.courseResources = courseResource;
	}

	/**
	 * standard getter for the attribute course_resource
	 * 
	 * @return a set of entries in the course_resource table which shows the resources in this course
	 */
	@OneToMany(mappedBy="course")
	public Set<CourseLearningObject> getCourseResources() {
		return this.courseResources;
	}

	/**
	 * standard add method for the attribute course_resource
	 * 
	 * @param courseResource
	 *            this entry of the course_resource table will be added to this course
	 */
	public void addCourseResource(final CourseLearningObject courseResource) {
		this.courseResources.add(courseResource);
	}
	
	/**
	 * standard setter for the attribute course_tasks
	 * 
	 * @param courseTasks
	 *            a set of entries in the course_resource table which shows the resources in this course
	 */
	public void setCourseTasks(final Set<CourseTask> courseTasks) {
		this.courseTasks = courseTasks;
	}

	/**
	 * standard getter for the attribute course_tasks
	 * 
	 * @return a set of entries in the course_task table which shows the tasks in this course
	 */
	@OneToMany(mappedBy="course")
	public Set<CourseTask> getCourseTasks() {
		return this.courseTasks;
	}

	/**
	 * standard add method for the attribute course_tasks
	 * 
	 * @param courseResource
	 *            this entry of the course_resource table will be added to this course
	 */
	public void addCourseTask(final CourseTask courseTask) {
		this.courseTasks.add(courseTask);
	}
	
	public void setEventLogs(final Set<ViewLog> eventLog) {
		this.eventLogs = eventLog;
	}

	@OneToMany(mappedBy="course")
	public Set<ViewLog> getEventLogs() {
		return this.eventLogs;
	}

	public void addEventLog(final ViewLog eventLog) {
		this.eventLogs.add(eventLog);
	}
	
	public void setTaskLogs(final Set<SubmissionLog> taskLog) {
		this.taskLogs = taskLog;
	}

	@OneToMany(mappedBy="course")
	public Set<SubmissionLog> getTaskLogs() {
		return this.taskLogs;
	}

	public void addTaskLog(final SubmissionLog taskLog) {
		this.taskLogs.add(taskLog);
	}
	
	public void setCollaborativeLogs(final Set<CollaborativeLog> collaborativeLog) {
		this.collaborativeLogs = collaborativeLog;
	}

	@OneToMany(mappedBy="course")
	public Set<CollaborativeLog> getCollaborativeLogs() {
		return this.collaborativeLogs;
	}

	public void addCollaborativeLog(final CollaborativeLog collaborativeLog) {
		this.collaborativeLogs.add(collaborativeLog);
	}

	/**
	 * @return the taskUsers
	 */
	@OneToMany(mappedBy="course")
	public Set<TaskUser> getTaskUsers() {
		return taskUsers;
	}

	/**
	 * @param taskUsers the taskUsers to set
	 */
	public void setTaskUsers(Set<TaskUser> taskUsers) {
		this.taskUsers = taskUsers;
	}
	
	public void addTaskUser(TaskUser taskUser)
	{
		this.taskUsers.add(taskUser);
	}

	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="course")
	public Set<CourseUser> getCourseUsers() {
		return courseUsers;
	}

	/**
	 * @param courseUsers the courseUsers to set
	 */
	public void setCourseUsers(Set<CourseUser> courseUsers) {
		this.courseUsers = courseUsers;
	}
	
	public void addCourseUser(CourseUser courseUser)
	{
		this.courseUsers.add(courseUser);
	}

	/**
	 * @return the platform
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="platform_id")
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	public void setPlatform(final long platform, final Map<Long, Platform> platforms,
			final Map<Long, Platform> oldPlatforms) {

		if (platforms.get(platform) != null)
		{
			this.platform = platforms.get(platform);
			platforms.get(platform).addCourse(this);
		}
		if ((this.platform == null) && (oldPlatforms.get(platform) != null))
		{
			this.platform = oldPlatforms.get(platform);
			oldPlatforms.get(platform).addCourse(this);
		}
	}


	
}
