/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/AssignGradesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/AssignGradesLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Mapping class for table Assign.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "mdl_assign_grades")
public class AssignGradesLMS {

	private long id;
	private long assignment;
	private long user;
	private Double grade;
	private long grader;
	private long timemodified;
	private long timecreated;
	
	@Id
	@Column(unique = true)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="assignment")
	public long getAssignment() {
		return assignment;
	}

	
	public void setAssignment(long assignment) {
		this.assignment = assignment;
	}

	@Column(name="userid")
	public long getUser() {
		return user;
	}

	
	public void setUser(long user) {
		this.user = user;
	}

	@Column(name="grade")
	public Double getGrade() {
		return grade;
	}
	
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	
	@Column(name="timemodified")
	public long getTimemodified() {
		return timemodified;
	}
	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	
	@Column(name="timecreated")
	public long getTimecreated() {
		return timecreated;
	}
	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name="grader")
	public long getGrader() {
		return grader;
	}

	public void setGrader(long grader) {
		this.grader = grader;
	}


}
