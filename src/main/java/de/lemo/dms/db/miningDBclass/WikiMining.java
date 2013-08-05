/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/WikiMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/WikiMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table wiki. */
@Entity
@Table(name = "wiki")
public class WikiMining implements IMappingClass, ILearningObject {

	private long id;
	private String title;
	private String summary;
	private long timeCreated;
	private long timeModified;
	private Long platform;
	private static final Long PREFIX = 18L;


	private Set<CourseWikiMining> courseWikis = new HashSet<CourseWikiMining>();
	private Set<WikiLogMining> wikiLogs = new HashSet<WikiLogMining>();

	@Transient
	public Long getPrefix()
	{
		return PREFIX;
	}
	
	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof WikiMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof WikiMining)) {
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
	 * @return the identifier of the wiki
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
	 *            the identifier of the wiki
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the wiki
	 */
	@Override
	@Column(name="title", length=1000)
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the wiki
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute summary
	 * 
	 * @return a short text which describes the subject of the wiki
	 */
	@Column(name="summary", length=1000)
	public String getSummary() {
		return this.summary;
	}

	/**
	 * standard setter for the attribute summary
	 * 
	 * @param summary
	 *            a short text which describes the subject of the wiki
	 */
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	/**
	 * standard getter for the attribute time created
	 * 
	 * @return the time stamp when the wiki was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute time created
	 * 
	 * @param timeCreated
	 *            the time stamp when the wiki was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute time modified
	 * 
	 * @return the time stamp when the quiz was changed the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute time modified
	 * 
	 * @param timeModified
	 *            the time stamp when the quiz was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute course_wiki
	 * 
	 * @param courseWikis
	 *            a set of entries in the course_wiki table which relate this wiki to courses
	 */
	public void setCourseWikis(final Set<CourseWikiMining> courseWikis) {
		this.courseWikis = courseWikis;
	}

	/**
	 * standard getter for the attribute course_wiki
	 * 
	 * @return a set of entries in the course_wiki table which relate this wiki to courses
	 */
	@OneToMany(mappedBy="wiki")
	public Set<CourseWikiMining> getCourseWikis() {
		return this.courseWikis;
	}

	/**
	 * standard add method for the attribute course_wiki
	 * 
	 * @param courseWiki
	 *            this entry will be added to the list of course_wiki in this wiki
	 */
	public void addCourseWiki(final CourseWikiMining courseWiki) {
		this.courseWikis.add(courseWiki);
	}

	/**
	 * standard setter for the attribute wiki_log
	 * 
	 * @param wikiLogs
	 *            a set of entries in the wiki_log table which are related to this wiki
	 */
	public void setWikiLogs(final Set<WikiLogMining> wikiLogs) {
		this.wikiLogs = wikiLogs;
	}

	/**
	 * standard getter for the attribute wiki_log
	 * 
	 * @return a set of entries in the wiki_log table which are related to this wiki
	 */
	@OneToMany(mappedBy="wiki")
	public Set<WikiLogMining> getWikiLogs() {
		return this.wikiLogs;
	}

	/**
	 * standard add method for the attribute wiki_log
	 * 
	 * @param wikiLog
	 *            this entry will be added to the list of wiki_log in this quiz
	 */
	public void addWikiLog(final WikiLogMining wikiLog) {
		this.wikiLogs.add(wikiLog);
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
