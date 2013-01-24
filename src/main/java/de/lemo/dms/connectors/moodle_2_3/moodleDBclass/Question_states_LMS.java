/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Question_states_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Question_states_LMS {

	private long id;
	private long attempt;
	private long question;
	private String answer;
	private short event;
	private double penalty;
	private double raw_grade;
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

	public double getRaw_grade() {
		return this.raw_grade;
	}

	public void setRaw_grade(final double rawGrade) {
		this.raw_grade = rawGrade;
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
