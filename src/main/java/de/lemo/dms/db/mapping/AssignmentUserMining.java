/**
 * File ./src/main/java/de/lemo/dms/db/mapping/AssignmentUserMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/AssignmentUserMining.java
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

/** This class represents the association between the assignment and the user. */
@Entity
@Table(name = "assignment_user")
public class AssignmentUserMining implements IMappingClass, IRatedUserAssociation {

	private long id;
	private UserMining user;
	private CourseMining course;
	private AssignmentMining assignment;
	private double finalGrade;
	private long timemodified;
	private Long platform;

	
	@Override
	@Transient
	public Long getPrefix() {
		return 11L;
	}
	
	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof AssignmentUserMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof AssignmentUserMining)) {
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
	 * @return the identifier for the association between assignment and user
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
	 *            the identifier for the association between assignment and user
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
			userMining.get(user).addAssignmentUser(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addAssignmentUser(this);
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
			courseMining.get(course).addAssignmentUser(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addAssignmentUser(this);
		}
	}

	/**
	 * standard getter for the attribute assignment
	 * 
	 * @return the assignment in which the action takes place
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assignment_id")
	public AssignmentMining getAssignment() {
		return this.assignment;
	}

	/**
	 * standard setter for the attribute assignment
	 * 
	 * @param assignment
	 *            the assignment in which the action takes place
	 */
	public void setAssignment(final AssignmentMining assignment) {
		this.assignment = assignment;
	}

	/**
	 * parameterized setter for the attribute assignment
	 * 
	 * @param id
	 *            the id of the assignment in which the action takes place
	 * @param assignmentMining
	 *            a list of new added assignment, which is searched for the assignment with the qid and qtype submitted in the other
	 *            parameters
	 * @param oldAssignmentMining
	 *            a list of assignment in the miningdatabase, which is searched for the assignment with the qid and qtype submitted
	 *            in the other parameters
	 */
	public void setAssignment(final long assignment, final Map<Long, AssignmentMining> assignmentMining,
			final Map<Long, AssignmentMining> oldAssignmentMining) {

		if (assignmentMining.get(assignment) != null)
		{
			this.assignment = assignmentMining.get(assignment);
			assignmentMining.get(assignment).addAssignmentUser(this);
		}
		if ((this.assignment == null) && (oldAssignmentMining.get(assignment) != null))
		{
			this.assignment = oldAssignmentMining.get(assignment);
			oldAssignmentMining.get(assignment).addAssignmentUser(this);
		}
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade of the user in this assignment
	 */
	@Column	(name="finalgrade")
	public double getFinalGrade() {
		return this.finalGrade;
	}

	/**
	 * standard setter for the attribute finalgrade
	 * 
	 * @param finalGrade
	 *            the final grade of the user in this assignment
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
	public long getTimemodified() {
		return this.timemodified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the grade was changed the last time
	 */
	public void setTimemodified(final long timeModified) {
		this.timemodified = timeModified;
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
	public Long getLearnObjId() {
		return this.getAssignment().getId();
	}

	@Override
	@Transient
	public Double getMaxGrade() {
		return this.getAssignment().getMaxGrade();
	}
}
