/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/QuestionStatesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Question_states_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table QuestionStates.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "mdl_question_states")
public class QuestionStatesLMS {

	private long id;
	private long attempt;
	private long question;
	private String answer;
	private short event;
	private double penalty;
	private double rawGrade;
	private double grade;
	private long timestamp;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="attempt")
	public long getAttempt() {
		return this.attempt;
	}

	public void setAttempt(final long attempt) {
		this.attempt = attempt;
	}

	@Column(name="question")
	public long getQuestion() {
		return this.question;
	}

	public void setQuestion(final long question) {
		this.question = question;
	}

	@Column(name="answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	@Column(name="event")
	public short getEvent() {
		return this.event;
	}

	public void setEvent(final short event) {
		this.event = event;
	}

	@Column(name="penalty")
	public double getPenalty() {
		return this.penalty;
	}

	public void setPenalty(final double penalty) {
		this.penalty = penalty;
	}

	@Column(name="raw_grade")
	public double getRawGrade() {
		return this.rawGrade;
	}

	public void setRawGrade(final double rawGrade) {
		this.rawGrade = rawGrade;
	}

	@Column(name="grade")
	public double getGrade() {
		return this.grade;
	}

	public void setGrade(final double grade) {
		this.grade = grade;
	}

	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}
}
