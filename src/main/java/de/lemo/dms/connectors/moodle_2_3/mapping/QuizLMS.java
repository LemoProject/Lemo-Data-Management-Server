/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/mapping/QuizLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/mapping/Quiz_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Quiz.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_quiz")
public class QuizLMS {

	private long id;
	private long course;
	private String name;
	private String intro;
	private String questions;
	private long timeopen;
	private long timeclose;
	private long sumgrade;
	private long grade;
	private long timecreated;
	private long timemodified;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="course")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	@Column(name="name")
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name="intro")
	public String getIntro() {
		return this.intro;
	}

	public void setIntro(final String intro) {
		this.intro = intro;
	}

	@Column(name="questions")
	public String getQuestions() {
		return this.questions;
	}

	public void setQuestions(final String questions) {
		this.questions = questions;
	}

	@Column(name="timeopen")
	public long getTimeopen() {
		return this.timeopen;
	}

	public void setTimeopen(final long timeopen) {
		this.timeopen = timeopen;
	}

	@Column(name="timeclose")
	public long getTimeclose() {
		return this.timeclose;
	}

	public void setTimeclose(final long timeclose) {
		this.timeclose = timeclose;
	}

	@Column(name="sumgrades")
	public long getSumgrade() {
		return this.sumgrade;
	}

	public void setSumgrade(final long sumgrade) {
		this.sumgrade = sumgrade;
	}

	@Column(name="grade")
	public long getGrade() {
		return this.grade;
	}

	public void setGrade(final long grade) {
		this.grade = grade;
	}

	@Column(name="timecreated")
	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

}
