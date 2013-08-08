/**
 * File ./src/main/java/de/lemo/dms/db/mapping/WikiLogMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/WikiLogMining.java
 * Date 2013-01-24
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

import de.lemo.dms.db.mapping.abstractions.ILogMining;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/** This class represents the log table for the wiki object. */
@Entity
@Table(name = "wiki_log")
public class WikiLogMining implements ILogMining, IMappingClass {

	private long id;
	private WikiMining wiki;
	private UserMining user;
	private CourseMining course;
	private String action;
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
		if (!(o instanceof WikiLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof WikiLogMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	@Override
	@Column(name="duration")
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	@Override
	@Transient
	public String getTitle()
	{
		return this.wiki == null ? null : this.wiki.getTitle();
	}

	@Override
	@Transient
	public Long getLearnObjId()
	{
		return this.wiki == null ? null : this.wiki.getId();
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the log entry
	 */
	@Override
	@Id
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
	 * standard getter for the attribute action
	 * 
	 * @return the action which occur
	 */
	@Override
	@Column(name="action")
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
	 * standard getter for the attribute
	 * 
	 * @return the time stamp the action did occur
	 */
	@Override
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute
	 * 
	 * @param timestamp
	 *            the time stamp the logged action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the the course in which the action on the wiki occur
	 */
	@Override
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of the course in which the action on the wiki occur as long value
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of courses in the mining database, which is searched for the course with the id submitted in
	 *            the course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addWikiLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addWikiLog(this);
		}
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the the course in which the action on the wiki occur
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the resource
	 */
	@Override
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public UserMining getUser() {
		return this.user;
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
	 * parameterized setter for the attribute
	 * 
	 * @param user
	 *            the id of the user who interact with the resource
	 * @param userMining
	 *            a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining
	 *            a list of user in the mining database, which is searched for the user with the id submitted in the
	 *            user parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addWikiLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addWikiLog(this);
		}
	}

	/**
	 * standard getter for the attribute wiki
	 * 
	 * @return the wiki with which was interacted
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="wiki_id")
	public WikiMining getWiki() {
		return this.wiki;
	}

	/**
	 * standard setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the wiki with which was interacted
	 */
	public void setWiki(final WikiMining wiki) {
		this.wiki = wiki;
	}

	/**
	 * parameterized setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the id of the wiki with which was interacted
	 * @param wikiMining
	 *            a list of new added wiki, which is searched for the wiki with the id submitted in the wiki parameter
	 * @param oldWikiMining
	 *            a list of wiki in the mining database, which is searched for the wiki with the id submitted in the
	 *            wiki parameter
	 */
	public void setWiki(final long wiki, final Map<Long, WikiMining> wikiMining,
			final Map<Long, WikiMining> oldWikiMining) {

		if (wikiMining.get(wiki) != null)
		{
			this.wiki = wikiMining.get(wiki);
			wikiMining.get(wiki).addWikiLog(this);
		}
		if ((this.wiki == null) && (oldWikiMining.get(wiki) != null))
		{
			this.wiki = oldWikiMining.get(wiki);
			oldWikiMining.get(wiki).addWikiLog(this);
		}
	}

	@Override
	@Transient
	public Long getPrefix() {
		return 18L;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
