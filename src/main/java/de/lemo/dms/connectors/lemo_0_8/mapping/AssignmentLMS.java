/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/AssignmentLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/AssignmentLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/** 
 * This class represents the table assignment. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "assignment")
public class AssignmentLMS{

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;
	private long timeCreated;
	private long timeModified;
	private Long platform;


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
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the assignment
	 */
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


	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}