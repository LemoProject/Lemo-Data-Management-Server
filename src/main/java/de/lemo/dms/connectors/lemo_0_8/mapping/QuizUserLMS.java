/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuizUserLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuizUserLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the association between the quiz and the user. */
@Entity
@Table(name = "quiz_user")
public class QuizUserLMS{

	private long id;
	private long user;
	private long course;
	private long quiz;
	private double rawGrade;
	private double finalGrade;
	private long timeModified;
	private Long platform;

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between quiz and user
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between quiz and user
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who is associated
	 */
	@Column(name="user_id")
	public long getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is associated
	 */
	public void setUser(final long user) {
		this.user = user;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Column(name="course_id")	 
	public long getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final long course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute quiz
	 * 
	 * @return the quiz in which the action takes place
	 */
	@Column(name="quiz_id")
	public long getQuiz() {
		return this.quiz;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the quiz in which the action takes place
	 */
	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	/**
	 * standard getter for the attribute rawgrade
	 * 
	 * @return the raw grade of the user in this exercise
	 */
	@Column	(name="rawgrade")
	public double getRawGrade() {
		return this.rawGrade;
	}

	/**
	 * standard setter for the attribute rawgrade
	 * 
	 * @param rawGrade
	 *            the raw grade of the user in this quiz
	 */
	public void setRawGrade(final double rawGrade) {
		this.rawGrade = rawGrade;
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade of the user in this quiz
	 */
	@Column	(name="finalgrade")
	public double getFinalGrade() {
		return this.finalGrade;
	}

	/**
	 * standard setter for the attribute finalgrade
	 * 
	 * @param finalGrade
	 *            the final grade of the user in this quiz
	 */
	public void setFinalGrade(final double finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the grade was changed the last time
	 */
	@Column	(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the grade was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	@Column	(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
