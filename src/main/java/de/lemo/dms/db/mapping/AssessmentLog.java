/**
 * File ./src/main/java/de/lemo/dms/db/mapping/TaskLog.java
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
 * File ./main/java/de/lemo/dms/db/mapping/TaskLog.java
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
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.db.mapping.abstractions.IMapping;
/**
 * This class represents the log table for the task_logs.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_assessment_log")
public class AssessmentLog implements IMapping, ILog{

	private long id;
	private Course course;
	private User user;
	private Assessment task;
	private long timestamp;
	private String answer;
	private String action;
	private long duration;
	private static Long PREFIX = 11L;
	
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof AssessmentLog)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof AssessmentLog)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	@Override
	public int compareTo(final ILog arg0) {
		ILog s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.timestamp > s.getTimestamp()) {
				return 1;
			}
			if (this.timestamp < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}
	
	@Column(name="duration")
	public Long getDuration() {
		return duration;
	}
	
	
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	@Id
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public Course getCourse() {
		return course;
	}
	
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assessment_id")
	public Assessment getTask() {
		return task;
	}
	
	
	public void setTask(Assessment task) {
		this.task = task;
	}
	
	
	@Column(name="timestamp")
	public long getTimestamp() {
		return timestamp;
	}
	
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	@Lob
	@Column(name="answer")
	public String getAnswer() {
		return answer;
	}
	
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public void setCourse(final long course, final Map<Long, Course> courses,
			final Map<Long, Course> oldCourses) {

		if (courses.get(course) != null)
		{
			this.course = courses.get(course);
			courses.get(course).addTaskLog(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addTaskLog(this);
		}
	}
	
	public void setUser(final long user, final Map<Long, User> users,
			final Map<Long, User> oldUsers) {

		if (users.get(user) != null)
		{
			this.user = users.get(user);
			users.get(user).addTaskLog(this);
		}
		if ((this.user == null) && (oldUsers.get(user) != null))
		{
			this.user = oldUsers.get(user);
			oldUsers.get(user).addTaskLog(this);
		}
	}
	
	public void setTask(final long task, final Map<Long, Assessment> tasks,
			final Map<Long, Assessment> oldTasks) {

		if (tasks.get(task) != null)
		{
			this.task = tasks.get(task);
			tasks.get(task).addTaskLog(this);
		}
		if ((this.task == null) && (oldTasks.get(task) != null))
		{
			this.task = oldTasks.get(task);
			oldTasks.get(task).addTaskLog(this);
		}
	}

	@Override
	@Transient
	public Long getLearningObjectId() {
		return this.getTask().getId();
	}

	@Override
	@Transient
	public ILearningObject getLearningObject() {
		return this.getTask();
	}

	@Override
	@Transient
	public String getTitle() {
		
		return this.getTask().getTitle();
	}

	@Override
	@Transient
	public long getPrefix() {
		return PREFIX;
	}

	@Override
	@Transient
	public String getType() {
		return "TASK";
	}

	/**
	 * @return the action
	 */
	@Column(name="action")
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
}
