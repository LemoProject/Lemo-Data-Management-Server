/**
 * File ./src/main/java/de/lemo/dms/db/mapping/AssignmentLogMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/AssignmentLogMining.java
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
import de.lemo.dms.db.mapping.abstractions.IRatedLogObject;

/** 
 * This class represents the log table for the assignment modules. 
 * @author Sebastian Schwarzrock
 *
 */
@Entity
@Table(name = "assignment_log")
public class AssignmentLogMining implements ILogMining, IMappingClass, IRatedLogObject {


	private long id;
	
	private UserMining user;
	
	private CourseMining course;
	
	private AssignmentMining assignment;
	

	private Double grade;
	

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
		if (!(o instanceof AssignmentLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof AssignmentLogMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}
	
	@Override
	@Transient
	public String getTitle()
	{
		return this.assignment == null ? null : this.assignment.getTitle();
	}

	@Override
	@Transient
	public Long getLearnObjId()
	{
		return this.assignment == null ? null : this.assignment.getId();
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade of the user in this quiz
	 */
	@Override
	@Transient
	public Double getFinalGrade() {
		return this.grade;
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
	 * standard getter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the log entry
	 */
	@Override
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the quiz
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
	 *            the user who interact with the quiz
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute quiz
	 * 
	 * @param user
	 *            the id of the user who interact with the quiz
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
			userMining.get(user).addAssignmentLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addAssignmentLog(this);
		}
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Override
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
			courseMining.get(course).addAssignmentLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addAssignmentLog(this);
		}
	}

	/**
	 * standard getter for the attribute grade
	 * 
	 * @return the grade in this case of action
	 */
	@Override
	@Column(name="grade")
	public Double getGrade() {
		return this.grade;
	}

	/**
	 * standard setter for the attribute grade
	 * 
	 * @param grade
	 *            the grade in this case of action
	 */
	public void setGrade(final Double grade) {
		this.grade = grade;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the action did occur
	 */
	@Override
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp the action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
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
	 * parameterized setter for the attribute assignment
	 * 
	 * @param id
	 *            the id of the assignment in which the action takes place
	 * @param assignmentMining
	 *            a list of new added assignments, which is searched for the assignment with the id submitted in the
	 *            parameter
	 * @param oldAssignmentMining
	 *            a list of quiz in the miningdatabase, which is searched for the assignment with the id submitted in
	 *            the parameter
	 */
	public void setAssignment(final long assignment, final Map<Long, AssignmentMining> assignmentMining,
			final Map<Long, AssignmentMining> oldAssignmentMining) {

		if (assignmentMining.get(assignment) != null)
		{
			this.assignment = assignmentMining.get(assignment);
			assignmentMining.get(assignment).addAssignmentLog(this);
		}
		if ((this.assignment == null) && (oldAssignmentMining.get(assignment) != null))
		{
			this.assignment = oldAssignmentMining.get(assignment);
			oldAssignmentMining.get(assignment).addAssignmentLog(this);
		}
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
	 * standard getter for the attribute assignment
	 * 
	 * @return the assignment in which the action takes place
	 */

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assignment_id")
	public AssignmentMining getAssignment() {
		return this.assignment;
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
	public Long getPrefix() {
		return 11L;
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
	public Double getMaxGrade() {
		return this.assignment.getMaxGrade();
	}
}
