/**
 * File ./src/main/java/de/lemo/dms/db/mapping/CollaborativeLog.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./main/java/de/lemo/dms/db/mapping/CollaborativeLog.java
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.db.mapping.abstractions.IMapping;

/**
 * This class represents the log table for the collaborative_Log objects.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_collaboration_log")
public class CollaborationLog implements IMapping, ILog{

	private long id;
	private Course course;
	private User user;
	private LearningObj learning;
	private Long timestamp;
	private String text;
	private String action;
	private CollaborationLog referrer;
	private static Long PREFIX = 15L;
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof CollaborationLog)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CollaborationLog)) {
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

	//@Lob
	@Column(name="text")
	public String getText() {
		return text;
	}



	public void setText(String text) {
		if( text != null && text.length() > 255)
			text = text.substring(0, 254);
		this.text = text;
	}



	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public CollaborationLog getReferrer() {
		return referrer;
	}




	public void setReferrer(CollaborationLog referrer) {
		this.referrer = referrer;
	}
	
	public void setReferrer(Long id, final Map<Long, CollaborationLog> collaborationLogs)
	{
		if (collaborationLogs.get(id) != null)
		{
			this.referrer = collaborationLogs.get(id);
		}
	}
	
	public void setCourse(final long course, final Map<Long, Course> courses,
			final Map<Long, Course> oldCourses) {

		if (courses.get(course) != null)
		{
			this.course = courses.get(course);
			courses.get(course).addCollaborativeLog(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addCollaborativeLog(this);
		}
	}
	
	public void setUser(final long user, final Map<Long, User> users,
			final Map<Long, User> oldUsers) {

		if (users.get(user) != null)
		{
			this.user = users.get(user);
			users.get(user).addCollaborativeLog(this);
		}
		if ((this.user == null) && (oldUsers.get(user) != null))
		{
			this.user = oldUsers.get(user);
			oldUsers.get(user).addCollaborativeLog(this);
		}
	}
	
	public void setLearning(final long learningId, final Map<Long, LearningObj> learningobjects,
			final Map<Long, LearningObj> oldLearningObjects) {

		if (learningobjects.get(learningId) != null)
		{
			this.learning = learningobjects.get(learningId);
			learningobjects.get(learningId).addCollaborationLog(this);
		}
		if ((this.learning == null) && (oldLearningObjects.get(learningId) != null))
		{
			this.learning = oldLearningObjects.get(learningId);
			oldLearningObjects.get(learningId).addCollaborationLog(this);
		}
	}

	@Override
	@Transient
	public long getPrefix() {
		return PREFIX;
	}

	@Override
	@Transient
	public String getType() {
		return "COLLABORATION";
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
	public Long getLearningId() {
		return this.learning.getId();
	}
}
