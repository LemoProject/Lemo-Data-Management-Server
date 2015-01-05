/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseMining.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseMining.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents the table course.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course")
public class CourseMining{

	private long id;
	private long startDate;
	private long enrolStart;
	private long enrolEnd;
	private long timeCreated;
	private long timeModified;
	private String title;
	private String shortname;
	private Long platform;

	
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the course
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the course
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute startdate
	 * 
	 * @return the timestamp when the course starts
	 */
	@Column(name="startdate")
	public long getStartDate() {
		return this.startDate;
	}

	/**
	 * standard setter for the attribute startdate
	 * 
	 * @param startDate
	 *            the timestamp when the course starts
	 */
	public void setStartDate(final long startDate) {
		this.startDate = startDate;
	}

	/**
	 * standard getter for the attribute enrolstart
	 * 
	 * @return the timestamp after that the students can enrol themselfs to the course
	 */
	@Column(name="enrolstart")
	public long getEnrolStart() {
		return this.enrolStart;
	}

	/**
	 * standard setter for the attribute enrolstart
	 * 
	 * @param enrolStart
	 *            the timestamp after that the students can enrol themselfs to the course
	 */
	public void setEnrolStart(final long enrolStart) {
		this.enrolStart = enrolStart;
	}

	/**
	 * standard getter for the attribute enrolend
	 * 
	 * @return the timestamp after that the students can not enrol themself any more
	 */
	@Column(name="enrolend")
	public long getEnrolEnd() {
		return this.enrolEnd;
	}

	/**
	 * standard setter for the attribute enrolend
	 * 
	 * @param enrolEnd
	 */
	public void setEnrolEnd(final long enrolEnd) {
		this.enrolEnd = enrolEnd;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the course was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the course was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the course was changes for the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the course was changes for the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	/**
	 * @return the title
	 */
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
	 * @return the shortname
	 */
	@Column(name="shortname")
	public String getShortname() {
		return shortname;
	}

	/**
	 * @param shortname the shortname to set
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
}
