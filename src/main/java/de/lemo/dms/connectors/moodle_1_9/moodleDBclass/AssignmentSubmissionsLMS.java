/**
 * File ./src/main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/AssignmentSubmissionsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_submissions_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9.moodleDBclass;

/**
 * Mapping class for table AssignmentSubmissions.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class AssignmentSubmissionsLMS {

	private long id;
	private long assignment;
	private String userid;
	private long grade;
	private long timecreated;
	private long timemodified;
	private long teacher;
	private long timemarked;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getAssignment() {
		return this.assignment;
	}

	public void setAssignment(final long assignment) {
		this.assignment = assignment;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public long getTeacher() {
		return this.teacher;
	}

	public void setTeacher(final long teacher) {
		this.teacher = teacher;
	}

	public long getTimemarked() {
		return this.timemarked;
	}

	public void setTimemarked(final long timemarked) {
		this.timemarked = timemarked;
	}

	public void setGrade(final long grade) {
		this.grade = grade;
	}

	public long getGrade() {
		return this.grade;
	}
}
