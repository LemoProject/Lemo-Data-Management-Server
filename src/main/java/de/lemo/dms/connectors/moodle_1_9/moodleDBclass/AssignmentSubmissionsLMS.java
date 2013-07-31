/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/AssignmentSubmissionsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/Assignment_submissions_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9.moodleDBclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table AssignmentSubmissions.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_assignment_submissions")
public class AssignmentSubmissionsLMS {

	private long id;
	private long assignment;
	private String userid;
	private long grade;
	private long timecreated;
	private long timemodified;
	private long teacher;
	private long timemarked;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="assignment")
	public long getAssignment() {
		return this.assignment;
	}

	public void setAssignment(final long assignment) {
		this.assignment = assignment;
	}

	@Column(name="userid")
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	@Column(name="timecreated")
	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	@Column(name="teacher")
	public long getTeacher() {
		return this.teacher;
	}

	public void setTeacher(final long teacher) {
		this.teacher = teacher;
	}

	@Column(name="timemarked")
	public long getTimemarked() {
		return this.timemarked;
	}

	public void setTimemarked(final long timemarked) {
		this.timemarked = timemarked;
	}

	public void setGrade(final long grade) {
		this.grade = grade;
	}

	@Column(name="grade")
	public long getGrade() {
		return this.grade;
	}
}
