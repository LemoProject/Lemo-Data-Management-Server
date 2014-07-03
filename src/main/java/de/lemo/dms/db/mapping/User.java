/**
 * File ./src/main/java/de/lemo/dms/db/mapping/User.java
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
 * File ./main/java/de/lemo/dms/db/mapping/User.java
 * Date 2014-02-04
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

import de.lemo.dms.db.mapping.abstractions.IMapping;


/** This class represents the table user. */
@Entity
@Table(name = "lemo_user")
public class User implements IMapping{
	


	private long id;
	private String login;
	
	private Set<UserAssessment> userAssessments = new HashSet<UserAssessment>();
	private Set<AssessmentLog> assessmentLogs = new HashSet<AssessmentLog>();
	private Set<AccessLog> accessLogs= new HashSet<AccessLog>();
	private Set<CollaborationLog> collaborationLogs = new HashSet<CollaborationLog>();
	private Set<CourseUser> courseUsers = new HashSet<CourseUser>();
	
	
	/**
	 * @return the taskLogs
	 */
	@OneToMany(mappedBy="user")
	public Set<AssessmentLog> getTaskLogs() {
		return assessmentLogs;
	}

	/**
	 * @param taskLogs the taskLogs to set
	 */
	public void setTaskLogs(Set<AssessmentLog> taskLogs) {
		this.assessmentLogs = taskLogs;
	}
	
	public void addTaskLog(AssessmentLog taskLog)
	{
		this.assessmentLogs.add(taskLog);
	}

	/**
	 * @return the eventLogs
	 */
	@OneToMany(mappedBy="user")
	public Set<AccessLog> getEventLogs() {
		return accessLogs;
	}

	/**
	 * @param eventLogs the eventLogs to set
	 */
	public void setEventLogs(Set<AccessLog> eventLogs) {
		this.accessLogs = eventLogs;
	}
	
	public void addEventLog(AccessLog eventLog)
	{
		this.accessLogs.add(eventLog);
	}

	/**
	 * @return the collaborativeLogs
	 */
	@OneToMany(mappedBy="user")
	public Set<CollaborationLog> getCollaborativeLogs() {
		return collaborationLogs;
	}

	/**
	 * @param collaborativeLogs the collaborativeLogs to set
	 */
	public void setCollaborativeLogs(Set<CollaborationLog> collaborativeLogs) {
		this.collaborationLogs = collaborativeLogs;
	}
	
	public void addCollaborativeLog(CollaborationLog collaborativeLog)
	{
		this.collaborationLogs.add(collaborativeLog);
	}

	/**
	 * @return the login
	 */
	@Column(name="login")
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	public boolean equals(final IMapping o) {
		if (!(o instanceof User)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof User)) {
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
	 * @return the taskUsers
	 */
	@OneToMany(mappedBy="user")
	public Set<UserAssessment> getTaskUsers() {
		return userAssessments;
	}

	/**
	 * @param taskUsers the taskUsers to set
	 */
	public void setTaskUsers(Set<UserAssessment> taskUsers) {
		this.userAssessments = taskUsers;
	}
	
	public void addTaskUser(UserAssessment taskUser)
	{
		this.userAssessments.add(taskUser);
	}

	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="user")
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

}
