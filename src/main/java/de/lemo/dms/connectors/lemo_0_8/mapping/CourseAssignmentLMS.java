/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseAssignmentLMS.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseAssignmentLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/** 
 * This class represents the relationship between the courses and assignments. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_assignment")
public class CourseAssignmentLMS{

	private long id;
	private long course;
	private long assignment;
	private Long platform;

	
	
	/**
	 * standard getter for the attribut course
	 * 
	 * @return a course in which the quiz is used
	 */
	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribut course
	 * 
	 * @param course
	 *            a course in which the quiz is used
	 */
	public void setCourse(final long course) {
		this.course = course;
	}

	

	/**
	 * standard getter for the attribut assignment
	 * 
	 * @return the quiz which is used in the course
	 */
	@Column(name="assignment_id")
	public long getAssignment() {
		return this.assignment;
	}

	/**
	 * standard setter for the attribut assignment
	 * 
	 * @param assignment
	 *            the assignment which is used in the course
	 */
	public void setAssignment(final long assignment) {
		this.assignment = assignment;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and assignment
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and assignment
	 */
	@Id
	public long getId() {
		return this.id;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}