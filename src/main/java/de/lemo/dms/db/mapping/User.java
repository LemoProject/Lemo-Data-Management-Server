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
	private String authentication;
	private long gender;
	
	private Set<TaskUser> taskUsers = new HashSet<TaskUser>();
	private Set<TaskLog> taskLogs = new HashSet<TaskLog>();
	private Set<LearningObjectLog> eventLogs= new HashSet<LearningObjectLog>();
	private Set<CollaborativeObjectLog> collaborativeLogs = new HashSet<CollaborativeObjectLog>();
	private Set<Assessment> assessments = new HashSet<Assessment>();
	private Set<CourseUser> courseUsers = new HashSet<CourseUser>();
	private Set<Assessment> graders = new HashSet<Assessment>();
	
	
	/**
	 * @return the taskLogs
	 */
	@OneToMany(mappedBy="user")
	public Set<TaskLog> getTaskLogs() {
		return taskLogs;
	}

	/**
	 * @param taskLogs the taskLogs to set
	 */
	public void setTaskLogs(Set<TaskLog> taskLogs) {
		this.taskLogs = taskLogs;
	}
	
	public void addTaskLog(TaskLog taskLog)
	{
		this.taskLogs.add(taskLog);
	}

	/**
	 * @return the eventLogs
	 */
	@OneToMany(mappedBy="user")
	public Set<LearningObjectLog> getEventLogs() {
		return eventLogs;
	}

	/**
	 * @param eventLogs the eventLogs to set
	 */
	public void setEventLogs(Set<LearningObjectLog> eventLogs) {
		this.eventLogs = eventLogs;
	}
	
	public void addEventLog(LearningObjectLog eventLog)
	{
		this.eventLogs.add(eventLog);
	}

	/**
	 * @return the collaborativeLogs
	 */
	@OneToMany(mappedBy="user")
	public Set<CollaborativeObjectLog> getCollaborativeLogs() {
		return collaborativeLogs;
	}

	/**
	 * @param collaborativeLogs the collaborativeLogs to set
	 */
	public void setCollaborativeLogs(Set<CollaborativeObjectLog> collaborativeLogs) {
		this.collaborativeLogs = collaborativeLogs;
	}
	
	public void addCollaborativeLog(CollaborativeObjectLog collaborativeLog)
	{
		this.collaborativeLogs.add(collaborativeLog);
	}

	/**
	 * @return the assessments
	 */
	@OneToMany(mappedBy="grader")
	public Set<Assessment> getAssessments() {
		return assessments;
	}

	/**
	 * @param assessments the assessments to set
	 */
	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}
	
	public void addAssessment(Assessment assessment)
	{
		this.assessments.add(assessment);
	}

	/**
	 * @return the login
	 */
	@Column(name="authentication")
	public String getLogin() {
		return authentication;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.authentication = login;
	}

	/**
	 * @return the gender
	 */
	@Column(name="gender")
	public long getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(long gender) {
		this.gender = gender;
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
	public Set<TaskUser> getTaskUsers() {
		return taskUsers;
	}

	/**
	 * @param taskUsers the taskUsers to set
	 */
	public void setTaskUsers(Set<TaskUser> taskUsers) {
		this.taskUsers = taskUsers;
	}
	
	public void addTaskUser(TaskUser taskUser)
	{
		this.taskUsers.add(taskUser);
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

	/**
	 * @return the graders
	 */
	@OneToMany(mappedBy="grader")
	public Set<Assessment> getGraders() {
		return graders;
	}

	/**
	 * @param graders the graders to set
	 */
	public void setGraders(Set<Assessment> graders) {
		this.graders = graders;
	}
	
	public void addGrader(Assessment grader)
	{
		this.graders.add(grader);
	}
	
}
