/**
 * File ./src/main/java/de/lemo/dms/db/mapping/EventLog.java
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
 * File ./main/java/de/lemo/dms/db/mapping/EventLog.java
 * Date 2014-02-04
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.db.mapping.abstractions.IMapping;

/**
 * This class represents the log table for the course object.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_access_log")
public class AccessLog implements IMapping, ILog{

	private long id;
	private Course course;
	private User user;
	private LearningObj learning;
	private Long timestamp;
	private String action;
	private Long duration;
	private static Long PREFIX = 16L;
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof AccessLog)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof AccessLog)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
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
	@JoinColumn(name="learning_id")
	public LearningObj getLearning() {
		return learning;
	}
	
	
	
	public void setLearning(LearningObj learningObject) {
		this.learning = learningObject;
	}
	
	
	@Column(name="timestamp")
	public Long getTimestamp() {
		return timestamp;
	}
	
	
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	@Column(name="duration")
	public Long getDuration() {
		return duration;
	}
	
	
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	
	
	public void setCourse(final long course, final Map<Long, Course> courses,
			final Map<Long, Course> oldCourses) {

		if (courses.get(course) != null)
		{
			this.course = courses.get(course);
			courses.get(course).addEventLog(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addEventLog(this);
		}
	}
	
	public void setUser(final long user, final Map<Long, User> users,
			final Map<Long, User> oldUsers) {

		if (users.get(user) != null)
		{
			this.user = users.get(user);
			users.get(user).addEventLog(this);
		}
		if ((this.user == null) && (oldUsers.get(user) != null))
		{
			this.user = oldUsers.get(user);
			oldUsers.get(user).addEventLog(this);
		}
	}
	
	public void setLearning(final long learningObject, final Map<Long, LearningObj> learningObjects,
			final Map<Long, LearningObj> oldLearningObjects) {

		if (learningObjects.get(learningObject) != null)
		{
			this.learning = learningObjects.get(learningObject);
			learningObjects.get(learningObject).addAccessLog(this);
		}
		if ((this.learning == null) && (oldLearningObjects.get(learningObject) != null))
		{
			this.learning = oldLearningObjects.get(learningObject);
			oldLearningObjects.get(learningObject).addAccessLog(this);
		}
	}

	@Transient
	public long getPrefix() {
		return PREFIX;
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

	@Transient
	@Override
	public String getType() {
		
		return "ACCESS";
	}

	@Transient
	@Override
	public Long getLearningId() {
		return this.learning.getId();
	}

}
