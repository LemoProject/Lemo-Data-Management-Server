/**
 * File ./src/main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/QuizLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table Quiz.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
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

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(final String intro) {
		this.intro = intro;
	}

	public String getQuestions() {
		return this.questions;
	}

	public void setQuestions(final String questions) {
		this.questions = questions;
	}

	public long getTimeopen() {
		return this.timeopen;
	}

	public void setTimeopen(final long timeopen) {
		this.timeopen = timeopen;
	}

	public long getTimeclose() {
		return this.timeclose;
	}

	public void setTimeclose(final long timeclose) {
		this.timeclose = timeclose;
	}

	public long getSumgrade() {
		return this.sumgrade;
	}

	public void setSumgrade(final long sumgrade) {
		this.sumgrade = sumgrade;
	}

	public long getGrade() {
		return this.grade;
	}

	public void setGrade(final long grade) {
		this.grade = grade;
	}

	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

}
