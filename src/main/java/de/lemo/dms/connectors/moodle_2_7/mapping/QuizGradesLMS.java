/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/QuizGradesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Quiz_grades_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table QuizGrades.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_quiz_grades")
public class QuizGradesLMS {

	private long id;
	private long userid;
	private long quiz;
	private double grade;
	private long timemodified;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="userid")
	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	@Column(name="grade")
	public double getGrade() {
		return this.grade;
	}

	public void setGrade(final double grade) {
		this.grade = grade;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	@Column(name="quiz")
	public long getQuiz() {
		return this.quiz;
	}
}
