/**
 * File ./src/main/java/de/lemo/dms/db/mapping/ScormMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/ScormMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

/** This class represents the table assignment. */
@Entity
@Table(name = "scorm")
public class ScormMining implements IMappingClass, ILearningObject, IRatedObject {

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;
	private long timeCreated;
	private long timeModified;
	private Long platform;


	private Set<ScormLogMining> scormLogs = new HashSet<ScormLogMining>();
	private Set<CourseScormMining> courseScorms = new HashSet<CourseScormMining>();
	private Set<ScormUserMining> scormUsers = new HashSet<ScormUserMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof ScormMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ScormMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the scorm package will be accessible after by students
	 */
	@Column(name="timeopen")
	public long getTimeOpen() {
		return this.timeOpen;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timeOpen
	 *            the timestamp the scorm package will be accessible after by students
	 */
	public void setTimeOpen(final long timeOpen) {
		this.timeOpen = timeOpen;
	}

	/**
	 * standard getter for the attribute timeclose
	 * 
	 * @return the timestamp after that the scorm package will be not accessible any more by students
	 */
	@Column(name="timeclose")
	public long getTimeClose() {
		return this.timeClose;
	}

	/**
	 * standard setter for the attribute timeclose
	 * 
	 * @param timeClose
	 *            the timestamp after that the scorm package will be not accessible any more by students
	 */
	public void setTimeClose(final long timeClose) {
		this.timeClose = timeClose;
	}

	/**
	 * standard getter for the attribute timeCreated
	 * 
	 * @return the timestamp when the scorm package was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timeCreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the scorm package was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timeModified
	 * 
	 * @return the timestamp when the scorm package was changed the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timeModified
	 * 
	 * @param timeModified
	 *            the timestamp when the scorm package was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the scorm package
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the scorm package
	 */
	@Override
	@Column(name="title", length=1000)
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the scorm package
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
	 *            the identifier of the scorm package
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute maxgrade
	 * 
	 * @return the maximum grade which is set for the scorm package
	 */
	@Override
	@Column(name="maxgrade")
	public Double getMaxGrade() {
		return this.maxGrade;
	}

	/**
	 * standard setter for the attribute maxgrade
	 * 
	 * @param maxGrade
	 *            the maximum grade which is set for the scorm package
	 */
	public void setMaxGrade(final double maxGrade) {
		this.maxGrade = maxGrade;
	}

	/**
	 * standard setter for the attribute type
	 * 
	 * @param type
	 *            the type of this scorm package
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute type
	 * 
	 * @return the type of this scorm package
	 */
	@Column(name="type")
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute scorm_log
	 * 
	 * @param scormLogs
	 *            a set of entries in the scorm_log table which are related with this scorm package
	 */
	public void setScormLogs(final Set<ScormLogMining> scormLogs) {
		this.scormLogs = scormLogs;
	}

	/**
	 * standard getter for the attribute scorm_log
	 * 
	 * @return a set of entries in the quiz_log table which are related with this scorm package
	 */
	@OneToMany(mappedBy="scorm")
	public Set<ScormLogMining> getScormLogs() {
		return this.scormLogs;
	}

	/**
	 * standard setter for the attribute scorm_log
	 * 
	 * @param scormLog
	 *            this entry will be added to the list of scorm_log in this scorm package
	 */
	public void addScormLog(final ScormLogMining scormLog) {
		this.scormLogs.add(scormLog);
	}

	/**
	 * standard setter for the attribute course_scorm
	 * 
	 * @param courseScorms
	 *            a set of entries in the course_scorm table which are related with this scorm package
	 */
	public void setCourseScorms(final Set<CourseScormMining> courseScorms) {
		this.courseScorms = courseScorms;
	}

	/**
	 * standard getter for the attribute course_scorm
	 * 
	 * @return a set of entries in the course_scorm table which are related with this scorm package
	 */
	@OneToMany(mappedBy="scorm")
	public Set<CourseScormMining> getCourseScorms() {
		return this.courseScorms;
	}

	/**
	 * standard setter for the attribute course_scorm
	 * 
	 * @param courseScorm
	 *            this entry will be added to the list of course_scorm in this scorm package
	 */
	public void addCourseScorm(final CourseScormMining courseScorm) {
		this.courseScorms.add(courseScorm);
	}
	
	/**
	 * standard setter for the attribute course_scorm
	 * 
	 * @param courseScorms
	 *            a set of entries in the course_scorm table which are related with this scorm package
	 */
	public void setScormUsers(final Set<ScormUserMining> scormsUsers) {
		this.scormUsers = scormsUsers;
	}

	/**
	 * standard getter for the attribute course_scorm
	 * 
	 * @return a set of entries in the course_scorm table which are related with this scorm package
	 */
	@OneToMany(mappedBy="scorm")
	public Set<ScormUserMining> getScormUsers() {
		return this.scormUsers;
	}

	/**
	 * standard setter for the attribute course_scorm
	 * 
	 * @param courseScorm
	 *            this entry will be added to the list of course_scorm in this scorm package
	 */
	public void addScormUser(final ScormUserMining scormUser) {
		this.scormUsers.add(scormUser);
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	@Transient
	public Long getPrefix() {
		return 17L;
	}
}