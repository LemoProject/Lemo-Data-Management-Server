/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuestionLogLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuestionLogLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the log table for the question object. */
@Entity
@Table(name = "question_log")
public class QuestionLogLMS{

	private long id;
	private long question;
	private long user;
	private long course;
	private long quiz;
	private String type;
	private double penalty;
	private double rawGrade;
	private double finalGrade;
	private String answers;
	private long timestamp;
	private String action;
	private Long duration;
	private Long platform;

	@Column(name="duration")
	public Long getDuration() {
		return this.duration;
	}


	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the log entry
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the log entry
	 */
	public void setId(final long id) {
		this.id = id;
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
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	/**
	 * standard getter for the attribute type
	 * 
	 * @return the type of the question
	 */
	@Column(name="type")
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute type
	 * 
	 * @param type
	 *            the type of the question
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute answers
	 * 
	 * @return the answers which are submitted with this action
	 */
	@Column(name="answers")
	public String getAnswers() {
		return this.answers;
	}

	/**
	 * standard setter for the attribute answers
	 * 
	 * @param answers
	 *            the answers which are submitted with this action
	 */
	public void setAnswers(final String answers) {
		this.answers = answers;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the action did occur
	 */
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp the action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribute penalty
	 * 
	 * @return the penalty on the grade in this action
	 */
	@Column(name="penalty")
	public double getPenalty() {
		return this.penalty;
	}

	/**
	 * standard setter for the attribute penalty
	 * 
	 * @param penalty
	 *            the penalty on the grade in this action
	 */
	public void setPenalty(final double penalty) {
		this.penalty = penalty;
	}

	/**
	 * standard getter for the attribute raw_grade
	 * 
	 * @return the raw grade in this action(grade without penalty)
	 */
	@Column(name="rawgrade")
	public double getRawGrade() {
		return this.rawGrade;
	}

	/**
	 * standard setter for the attribute raw_grade
	 * 
	 * @param rawGrade
	 *            the raw grade in this action(grade without penalty)
	 */
	public void setRawGrade(final Double rawGrade) {
		this.rawGrade = rawGrade;
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade in this action(with penalty)
	 */
	@Column(name="finalgrade")
	public Double getFinalGrade() {
		return this.finalGrade;
	}

	/**
	 * standard setter for the attribute finalgrade
	 * 
	 * @param finalGrade
	 *            the final grade in this action(with penalty)
	 */
	public void setFinalGrade(final Double finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * standard getter for the attribute question
	 * 
	 * @return the question with which is interacted
	 */
	@Column(name="question_id")
	public long getQuestion() {
		return this.question;
	}

	/**
	 * standard setter for the attribute question
	 * 
	 * @param question
	 *            the question with which is interacted
	 */
	public void setQuestion(final long question) {
		this.question = question;
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
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the question
	 */
	@Column(name="user_id")
	public long getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the question
	 */
	public void setUser(final long user) {
		this.user = user;
	}
	
	/**
	 * standard setter for the attribute action
	 * 
	 * @param action
	 *            the action which occur
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * standard getter for the attribute action
	 * 
	 * @return the action which occur
	 */
	@Column(name="action")
	public String getAction() {
		return this.action;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
