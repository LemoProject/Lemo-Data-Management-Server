/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseUserLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseUserLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents the relationship between courses and user
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_user")
public class CourseUserLMS{

	private long id;
	private long course;
	private long user;
	private long role;
	private long enrolStart;
	private long enrolEnd;
	private Long platform;

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and user
	 */
	@Id
	public long getId() {
		return this.id;
	}
	
	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and user
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the user is enroled
	 */
	public void setCourse(final long course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the user is enroled
	 */
	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	
	/**
	 * standard getter for the attribute enrolstart
	 * 
	 * @return the timestamp when the user was enroled in the course
	 */
	@Column(name="enrolstart")
	public long getEnrolstart() {
		return this.enrolStart;
	}

	/**
	 * standard setter for the attribute enrolstart
	 * 
	 * @param enrolstart
	 *            the timestamp when the user was enroled in the course
	 */
	public void setEnrolstart(final long enrolstart) {
		this.enrolStart = enrolstart;
	}

	/**
	 * standard getter for the attribute enrolend
	 * 
	 * @return the timestamp when the user is not enroled any more
	 */
	@Column(name="enrolend")
	public long getEnrolend() {
		return this.enrolEnd;
	}

	/**
	 * standard setter for the attribute enrolend
	 * 
	 * @param enrolend
	 *            the timestamp when the user is not enroled any more
	 */
	public void setEnrolend(final long enrolend) {
		this.enrolEnd = enrolend;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who is enroled in the course
	 */
	@Column(name="user_id")
	public long getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is enroled in the course
	 */
	public void setUser(final long user) {
		this.user = user;
	}

	/**
	 * standard setter for the attribute role
	 * 
	 * @param role
	 *            the role which the user has in the course
	 */
	public void setRole(final long role) {
		this.role = role;
	}

	/**
	 * standard getter for the attribute role
	 * 
	 * @return the role which the user has in the course
	 */
	@Column(name="role_id")
	public long getRole() {
		return this.role;
	}

	
	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
