/**
 * File ./src/main/java/de/lemo/dms/db/mapping/Task.java
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
 * File ./main/java/de/lemo/dms/db/mapping/Task.java
 * Date 2014-02-04
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;


import java.util.HashSet;
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

/** 
 * This class represents the table task. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_task")
public class Task implements IMapping{

	private long id;
	private String title;
	private Long platform;
	private TaskType type;
	private Task parent;
	private double maxGrade;
	private long timeopen;
	private long timeclose;
	
	private Set<CourseTask> courseTasks = new HashSet<CourseTask>();
	private Set<TaskUser> taskUsers = new HashSet<TaskUser>();
	private Set<TaskLog> taskLogs = new HashSet<TaskLog>();
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof Task)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Task)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Lob
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	@Column(name="timeopen")
	public long getTimeopen() {
		return timeopen;
	}

	public void setTimeopen(long timeopen) {
		this.timeopen = timeopen;
	}

	@Column(name="timeclose")
	public long getTimeclose() {
		return timeclose;
	}

	public void setTimeclose(long timeclose) {
		this.timeclose = timeclose;
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseResources
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setCourseTasks(final Set<CourseTask> courseTasks) {
		this.courseTasks = courseTasks;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="task")
	public Set<CourseTask> getCourseTasks() {
		return this.courseTasks;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseResource
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addCourseTask(final CourseTask courseTask) {
		this.courseTasks.add(courseTask);
	}
	
	public void setTaskUsers(final Set<TaskUser> taskUsers) {
		this.taskUsers = taskUsers;
	}


	@OneToMany(mappedBy="task")
	public Set<TaskUser> getTaskUsers() {
		return this.taskUsers;
	}

	public void addTaskUser(final TaskUser taskUsers) {
		this.taskUsers.add(taskUsers);
	}

	/**
	 * @return the taskLogs
	 */
	@OneToMany(mappedBy="task")
	public Set<TaskLog> getTaskLogs() {
		return taskLogs;
	}

	/**
	 * @param taskLogs the taskLogs to set
	 */
	public void setTaskLogs(Set<TaskLog> taskLogs) {
		this.taskLogs = taskLogs;
	}
	
	public void addTaskLog(TaskLog taskLog)
	{
		this.taskLogs.add(taskLog);
	}

	/**
	 * @return the maxGrade
	 */
	public double getMaxGrade() {
		return maxGrade;
	}

	/**
	 * @param maxGrade the maxGrade to set
	 */
	public void setMaxGrade(double maxGrade) {
		this.maxGrade = maxGrade;
	}

}
