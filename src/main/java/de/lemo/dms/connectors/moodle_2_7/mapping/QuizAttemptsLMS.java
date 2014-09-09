/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/QuizAttemptsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Quiz_attempts_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table QuizAttempts.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "mdl_quiz_attempts")
public class QuizAttemptsLMS {

	private long id;
	private long uniqueid;
	private Double sumgrades;
	private long attempt;
	private String userid;
	private long quiz;
	private long timestart;
	private long timefinish;
	private long timemodified;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="uniqueid")
	public long getUniqueid() {
		return this.uniqueid;
	}

	public void setUniqueid(final long uniqueid) {
		this.uniqueid = uniqueid;
	}

	@Column(name="attempt")
	public long getAttempt() {
		return this.attempt;
	}

	public void setAttempt(final long attempt) {
		this.attempt = attempt;
	}

	@Column(name="userid")
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	@Column(name="quiz")
	public long getQuiz() {
		return this.quiz;
	}

	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	@Column(name="timestart")
	public long getTimestart() {
		return this.timestart;
	}

	public void setTimestart(final long timestart) {
		this.timestart = timestart;
	}

	@Column(name="timefinish")
	public long getTimefinish() {
		return this.timefinish;
	}

	public void setTimefinish(final long timefinish) {
		this.timefinish = timefinish;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public void setSumgrades(final Double sumgrades) {
		this.sumgrades = sumgrades;
	}

	@Column(name="sumgrades")
	public Double getSumgrades() {
		return this.sumgrades;
	}
}
