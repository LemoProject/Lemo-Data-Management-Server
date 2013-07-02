/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/ForumLogMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/ForumLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the log table for the forum object.
 * @author Sebastian Schwarzrock
 */
public class ForumLogMining implements ILogMining, IMappingClass {

	private long id;
	private ForumMining forum;
	private UserMining user;
	private CourseMining course;
	private String action;
	private String subject;
	private String message;
	private long timestamp;
	private Long duration;
	private Long platform;

	@Override
	public int compareTo(final ILogMining arg0) {
		ILogMining s;
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

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (o != null) {} else return false;
		if (!(o instanceof ForumLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ForumLogMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	@Override
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	@Override
	public String getTitle()
	{
		return this.forum == null ? null : this.forum.getTitle();
	}

	@Override
	public Long getLearnObjId()
	{
		return this.forum == null ? null : this.forum.getId();
	}

	/**
	 * standard getter for the attribut id
	 * 
	 * @return the identifier of the log entry
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribut id
	 * 
	 * @param id
	 *            the identifier of the log entry
	 */
	@Override
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribut subject
	 * 
	 * @return the subject of the entry
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * standard setter for the attribut subject
	 * 
	 * @param subject
	 *            the subject of the entry
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	/**
	 * standard getter for the attribut message
	 * 
	 * @return the message of the entry
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * standard setter for the attribut message
	 * 
	 * @param message
	 *            the message of the entry
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * standard getter for the attribut timestamp
	 * 
	 * @return the time when the logged action occur
	 */
	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribut timestamp
	 * 
	 * @param timestamp
	 *            the time when the logged action occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribut action
	 * 
	 * @return the action which occur
	 */
	@Override
	public String getAction() {
		return this.action;
	}

	/**
	 * standard setter for the attribut action
	 * 
	 * @param action
	 *            the action which occur
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * standard getter for the attribut course
	 * 
	 * @return the course in which the action takes place
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribut course
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
			courseMining.get(course).addForumLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addForumLog(this);
		}
	}

	/**
	 * standard setter for the attribut course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribut forum
	 * 
	 * @param forum
	 *            the id of the forum with which was interacted
	 * @param forumMining
	 *            a list of new added forum, which is searched for the forum with the id submitted in the forum
	 *            parameter
	 * @param oldForumMining
	 *            a list of forum in the miningdatabase, which is searched for the forum with the id submitted in the
	 *            forum parameter
	 */
	public void setForum(final long forum, final Map<Long, ForumMining> forumMining,
			final Map<Long, ForumMining> oldForumMining) {

		if (forumMining.get(forum) != null)
		{
			this.forum = forumMining.get(forum);
			forumMining.get(forum).addForumLog(this);
		}
		if ((this.forum == null) && (oldForumMining.get(forum) != null))
		{
			this.forum = oldForumMining.get(forum);
			oldForumMining.get(forum).addForumLog(this);
		}
	}

	/**
	 * standard getter for the attribut forum
	 * 
	 * @return the forum with which was interacted
	 */
	public ForumMining getForum() {
		return this.forum;
	}

	/**
	 * standard setter for the attribut forum
	 * 
	 * @param forum
	 *            the forum with which was interacted
	 */
	public void setForum(final ForumMining forum) {
		this.forum = forum;
	}

	/**
	 * standard setter for the attribut user
	 * 
	 * @param user
	 *            the user who interact with the forum
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * standard getter for the attribut user
	 * 
	 * @return the user who interact with the forum
	 */
	@Override
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the id of the user who interact with the resource
	 * @param userMining
	 *            a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining
	 *            a list of user in the miningdatabase, which is searched for the user with the id submitted in the user
	 *            parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addForumLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addForumLog(this);
		}
	}

	@Override
	public Long getPrefix() {
		return 15L;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
