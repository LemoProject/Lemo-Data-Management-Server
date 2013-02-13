/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_attempts_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class QuizAttemptsLMS {

	private long id;
	private long uniqueid;
	private double sumgrades;
	private long attempt;
	private long userid;
	private long quiz;
	private long timestart;
	private long timefinish;
	private long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getUniqueid() {
		return this.uniqueid;
	}

	public void setUniqueid(final long uniqueid) {
		this.uniqueid = uniqueid;
	}

	public long getAttempt() {
		return this.attempt;
	}

	public void setAttempt(final long attempt) {
		this.attempt = attempt;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getQuiz() {
		return this.quiz;
	}

	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	public long getTimestart() {
		return this.timestart;
	}

	public void setTimestart(final long timestart) {
		this.timestart = timestart;
	}

	public long getTimefinish() {
		return this.timefinish;
	}

	public void setTimefinish(final long timefinish) {
		this.timefinish = timefinish;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public void setSumgrades(final double sumgrades) {
		this.sumgrades = sumgrades;
	}

	public double getSumgrades() {
		return this.sumgrades;
	}
}
