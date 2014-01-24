/**
 * File ./src/main/java/de/lemo/dms/db/mapping/AssignmentMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/AssignmentMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

/** 
 * This class represents the table assignment. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "assignment")
public class AssignmentMining implements IMappingClass, ILearningObject, IRatedObject {

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;
	private long timeCreated;
	private long timeModified;
	private Long platform;

	private Set<AssignmentLogMining> assignmentLogs = new HashSet<AssignmentLogMining>();
	private Set<CourseAssignmentMining> courseAssignments = new HashSet<CourseAssignmentMining>();
	private Set<AssignmentUserMining> assignmentUsers = new HashSet<AssignmentUserMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof AssignmentMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof AssignmentMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int)id;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the assignment will be accessible after by students
	 */
	@Column(name="timeopen")
	public long getTimeOpen() {
		return this.timeOpen;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timeOpen
	 *            the timestamp the assignment will be accessible after by students
	 */
	public void setTimeOpen(final long timeOpen) {
		this.timeOpen = timeOpen;
	}

	/**
	 * standard getter for the attribute timeclose
	 * 
	 * @return the timestamp after that the assignment will be not accessible any more by students
	 */
	@Column(name="timeclose")
	public long getTimeClose() {
		return this.timeClose;
	}

	/**
	 * standard setter for the attribute timeclose
	 * 
	 * @param timeClose
	 *            the timestamp after that the assignment will be not accessible any more by students
	 */
	public void setTimeClose(final long timeClose) {
		this.timeClose = timeClose;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the assignment was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the assignment was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the assignment was changed the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the assignment was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the assignment
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the assignment
	 */
	@Override
	@Lob
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the assignment
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
	 *            the identifier of the assignment
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute maxgrade
	 * 
	 * @return the maximum grade which is set for the assignment
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
	 *            the maximum grade which is set for the assignment
	 */
	public void setMaxGrade(final double maxGrade) {
		this.maxGrade = maxGrade;
	}

	/**
	 * standard setter for the attribute type
	 * 
	 * @param type
	 *            the type of this assignment
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute type
	 * 
	 * @return the type of this assignment
	 */
	@Column(name="type")
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute assignment_log
	 * 
	 * @param assignmentLog
	 *            a set of entries in the assignment_log table which are related with this assignment
	 */
	public void setAssignmentLogs(final Set<AssignmentLogMining> assignmentLog) {
		this.assignmentLogs = assignmentLog;
	}

	/**
	 * standard getter for the attribute assignment_log
	 * 
	 * @return a set of entries in the assignment_log table which are related with this assignment
	 */
	@OneToMany(mappedBy="assignment")
	public Set<AssignmentLogMining> getAssignmentLogs() {
		return this.assignmentLogs;
	}

	/**
	 * standard setter for the attribute assignment_log
	 * 
	 * @param assignmentLog
	 *            this entry will be added to the list of assignment_log in this assignment
	 */
	public void addAssignmentLog(final AssignmentLogMining assignmentLog) {
		this.assignmentLogs.add(assignmentLog);
	}

	/**
	 * standard setter for the attribute course_assignment
	 * 
	 * @param courseAssignment
	 *            a set of entries in the course_assignment table which are related with this assignment
	 */
	public void setCourseAssignments(final Set<CourseAssignmentMining> courseAssignment) {
		this.courseAssignments = courseAssignment;
	}

	/**
	 * standard getter for the attribute course_assignment
	 * 
	 * @return a set of entries in the course_assignment table which are related with this assignment
	 */
	@OneToMany(mappedBy="assignment")
	public Set<CourseAssignmentMining> getCourseAssignments() {
		return this.courseAssignments;
	}

	/**
	 * standard setter for the attribute course_assignment
	 * 
	 * @param courseAssignment
	 *            this entry will be added to the list of course_assignment in this assignment
	 */
	public void addCourseAssignment(final CourseAssignmentMining courseAssignment) {
		this.courseAssignments.add(courseAssignment);
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
		return 11L;
	}
	
	/**
	 * standard setter for the attribute assignment_user
	 * 
	 * @param assignmentUsers
	 *            a set of entries in the assignment_user table which relate the assignment to user
	 */
	public void setAssignmentUsers(final Set<AssignmentUserMining> assignmentUsers) {
		this.assignmentUsers = assignmentUsers;
	}

	/**
	 * standard getter for the attribute assignment_user
	 * 
	 * @return a set of entries in the assignment_user table which relate the assignment to user
	 */
	@OneToMany(mappedBy="assignment")
	public Set<AssignmentUserMining> getAssignmentUsers() {
		return this.assignmentUsers;
	}

	/**
	 * standard add method for the attribute assignment_user
	 * 
	 * @param assignmentUser
	 *            this entry will be added to the list of assignment_user in this assignment
	 */
	public void addAssignmentUser(final AssignmentUserMining assignmentUser) {
		this.assignmentUsers.add(assignmentUser);
	}
}