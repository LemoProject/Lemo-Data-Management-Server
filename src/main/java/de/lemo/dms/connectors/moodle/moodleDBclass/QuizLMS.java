/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/QuizLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

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
