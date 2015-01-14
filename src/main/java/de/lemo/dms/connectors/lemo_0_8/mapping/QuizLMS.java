/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuizLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuizLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the table quiz. */
@Entity
@Table(name = "quiz")
public class QuizLMS{

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;
	private Long platform;
	private long timeCreated;
	private long timeModified;

	@Column(name="type")
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the quiz will be accessible after by students
	 */
	@Column(name="timeopen")
	public long getTimeOpen() {
		return this.timeOpen;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timeopen
	 *            the timestamp the quiz will be accessible after by students
	 */
	public void setTimeOpen(final long timeopen) {
		this.timeOpen = timeopen;
	}

	/**
	 * standard getter for the attribute timeClose
	 * 
	 * @return the timestamp after that the quiz will be not accessible any more by students
	 */
	@Column(name="timeclose")
	public long getTimeClose() {
		return this.timeClose;
	}

	/**
	 * standard setter for the attribute timeClose
	 * 
	 * @param timeClose
	 *            the timestamp after that the quiz will be not accessible any more by students
	 */
	public void setTimeClose(final long timeClose) {
		this.timeClose = timeClose;
	}

	/**
	 * standard getter for the attribute timeCreated
	 * 
	 * @return the timestamp when the quiz was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timeCreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the quiz was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timeModified
	 * 
	 * @return the timestamp when the quiz was changed the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timeModified
	 * 
	 * @param timeModified
	 *            the timestamp when the quiz was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the quiz
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the quiz
	 */
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the identifier for this quiz
	 */
	public void setId(final long quiz) {
		this.id = quiz;
	}

	/**
	 * standard getter for the attribute qid
	 * 
	 * @return the identifier for this quiz
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard getter for the attribute maxGrade
	 * 
	 * @return the maximum grade which is set for the quiz
	 */
	@Column(name="maxgrade")
	public Double getMaxGrade() {
		return this.maxGrade;
	}

	/**
	 * standard setter for the attribute maxGrade
	 * 
	 * @param maxGrade
	 *            the maximum grade which is set for the quiz
	 */
	public void setMaxGrade(final double maxGrade) {
		this.maxGrade = maxGrade;
	}


	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
