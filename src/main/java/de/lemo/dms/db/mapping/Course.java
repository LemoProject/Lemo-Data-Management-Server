/**
 * File ./src/main/java/de/lemo/dms/db/mapping/Course.java
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
 * File ./main/java/de/lemo/dms/db/mapping/Course.java
 * Date 2014-02-04
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

import de.lemo.dms.db.mapping.abstractions.IMapping;

/** This class represents the table course. */
@Entity
@Table(name = "lemo_course")
public class Course implements IMapping{
	
	private long id;
	private String title;
	
	private Set<CourseLearning> courseLearnings = new HashSet<CourseLearning>();
	private Set<AccessLog> accessLogs = new HashSet<AccessLog>();
	private Set<AssessmentLog> assessmentLogs = new HashSet<AssessmentLog>();
	private Set<CollaborationLog> collaborativeLogs = new HashSet<CollaborationLog>();
	private Set<UserAssessment> userAssessments = new HashSet<UserAssessment>();
	private Set<CourseUser> courseUsers = new HashSet<CourseUser>();
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof Course)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Course)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	@Lob
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	
	/**
	 * standard setter for the attribute course_resource
	 * 
	 * @param courseResource
	 *            a set of entries in the course_resource table which shows the resources in this course
	 */
	public void setCourseLearnings(final Set<CourseLearning> courseLearnings) {
		this.courseLearnings = courseLearnings;
	}

	/**
	 * standard getter for the attribute course_resource
	 * 
	 * @return a set of entries in the course_resource table which shows the resources in this course
	 */
	@OneToMany(mappedBy="course")
	public Set<CourseLearning> getCourseLearnings() {
		return this.courseLearnings;
	}

	/**
	 * standard add method for the attribute course_resource
	 * 
	 * @param courseResource
	 *            this entry of the course_resource table will be added to this course
	 */
	public void addCourseLearning(final CourseLearning courseLearning) {
		this.courseLearnings.add(courseLearning);
	}
	
		
	public void setEventLogs(final Set<AccessLog> eventLog) {
		this.accessLogs = eventLog;
	}

	@OneToMany(mappedBy="course")
	public Set<AccessLog> getEventLogs() {
		return this.accessLogs;
	}

	public void addEventLog(final AccessLog eventLog) {
		this.accessLogs.add(eventLog);
	}
	
	public void setAssessmentLogs(final Set<AssessmentLog> assessmentLog) {
		this.assessmentLogs = assessmentLog;
	}

	@OneToMany(mappedBy="course")
	public Set<AssessmentLog> getAssessmentLogs() {
		return this.assessmentLogs;
	}

	public void addTaskLog(final AssessmentLog assessmentLog) {
		this.assessmentLogs.add(assessmentLog);
	}
	
	public void setCollaborativeLogs(final Set<CollaborationLog> collaborativeLog) {
		this.collaborativeLogs = collaborativeLog;
	}

	@OneToMany(mappedBy="course")
	public Set<CollaborationLog> getCollaborativeLogs() {
		return this.collaborativeLogs;
	}

	public void addCollaborativeLog(final CollaborationLog collaborativeLog) {
		this.collaborativeLogs.add(collaborativeLog);
	}



	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="course")
	public Set<CourseUser> getCourseUsers() {
		return courseUsers;
	}

	/**
	 * @param courseUsers the courseUsers to set
	 */
	public void setCourseUsers(Set<CourseUser> courseUsers) {
		this.courseUsers = courseUsers;
	}
	
	public void addCourseUser(CourseUser courseUser)
	{
		this.courseUsers.add(courseUser);
	}

	/**
	 * @return the userAssessments
	 */
	@OneToMany(mappedBy="course")
	public Set<UserAssessment> getUserAssessments() {
		return userAssessments;
	}

	/**
	 * @param userAssessments the userAssessments to set
	 */
	public void setUserAssessments(Set<UserAssessment> userAssessments) {
		this.userAssessments = userAssessments;
	}
	
	public void addUserAssessment(UserAssessment userAssessment)
	{
		this.userAssessments.add(userAssessment);
	}
}
