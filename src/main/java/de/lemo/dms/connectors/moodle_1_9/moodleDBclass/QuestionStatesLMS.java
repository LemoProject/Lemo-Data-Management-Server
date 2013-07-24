/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/QuestionStatesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/Question_states_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9.moodleDBclass;

/**
 * Mapping class for table QuestionStates.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
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

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getAttempt() {
		return this.attempt;
	}

	public void setAttempt(final long attempt) {
		this.attempt = attempt;
	}

	public long getQuestion() {
		return this.question;
	}

	public void setQuestion(final long question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public short getEvent() {
		return this.event;
	}

	public void setEvent(final short event) {
		this.event = event;
	}

	public double getPenalty() {
		return this.penalty;
	}

	public void setPenalty(final double penalty) {
		this.penalty = penalty;
	}

	public double getRawGrade() {
		return this.rawGrade;
	}

	public void setRawGrade(final double rawGrade) {
		this.rawGrade = rawGrade;
	}

	public double getGrade() {
		return this.grade;
	}

	public void setGrade(final double grade) {
		this.grade = grade;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}
}
