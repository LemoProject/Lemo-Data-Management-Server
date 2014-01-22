/**
 * File ./src/main/java/de/lemo/dms/db/mapping/ScormUserMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/ScormUserMining.java
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

import de.lemo.dms.db.mapping.abstractions.IMappingClass;
import de.lemo.dms.db.mapping.abstractions.IRatedUserAssociation;

/** This class represents the association between the scorm and the user. */
@Entity
@Table(name = "scorm_user")
public class ScormUserMining implements IMappingClass, IRatedUserAssociation {

	private long id;
	private UserMining user;
	private CourseMining course;
	private ScormMining scorm;
	private double finalGrade;
	private long timeModified;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof ScormUserMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ScormUserMining)) {
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
	 * @return the identifier for the association between scorm and user
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
	 *            the identifier for the association between scorm and user
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who is associated
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is associated
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the id of the user who is associated
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
			userMining.get(user).addScormUser(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addScormUser(this);
		}

	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")	 
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribute course
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
			courseMining.get(course).addScormUser(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addScormUser(this);
		}
	}

	/**
	 * standard getter for the attribute scorm
	 * 
	 * @return the scorm in which the action takes place
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="scorm_id")
	public ScormMining getScorm() {
		return this.scorm;
	}

	/**
	 * standard setter for the attribute scorm
	 * 
	 * @param scorm
	 *            the scorm in which the action takes place
	 */
	public void setScorm(final ScormMining scorm) {
		this.scorm = scorm;
	}

	/**
	 * parameterized setter for the attribute scorm
	 * 
	 * @param id
	 *            the id of the scorm in which the action takes place
	 * @param scormMining
	 *            a list of new added scorm, which is searched for the scorm with the qid and qtype submitted in the other
	 *            parameters
	 * @param oldScormMining
	 *            a list of scorm in the miningdatabase, which is searched for the scorm with the qid and qtype submitted
	 *            in the other parameters
	 */
	public void setScorm(final long scorm, final Map<Long, ScormMining> scormMining,
			final Map<Long, ScormMining> oldScormMining) {

		if (scormMining.get(scorm) != null)
		{
			this.scorm = scormMining.get(scorm);
			scormMining.get(scorm).addScormUser(this);
		}
		if ((this.scorm == null) && (oldScormMining.get(scorm) != null))
		{
			this.scorm = oldScormMining.get(scorm);
			oldScormMining.get(scorm).addScormUser(this);
		}
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade of the user in this scorm
	 */
	@Column	(name="finalgrade")
	public double getFinalGrade() {
		return this.finalGrade;
	}

	/**
	 * standard setter for the attribute finalgrade
	 * 
	 * @param finalGrade
	 *            the final grade of the user in this scorm
	 */
	public void setFinalGrade(final double finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the grade was changed the last time
	 */
	@Column	(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the grade was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	@Column	(name="platform")
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

	@Override
	@Transient
	public Long getLearnObjId() {
		return this.getScorm().getId();
	}

	@Override
	@Transient
	public Double getMaxGrade() {
		return this.getScorm().getMaxGrade();
	}
}
