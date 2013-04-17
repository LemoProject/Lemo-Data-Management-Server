/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_attempts_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table QuizAttempts.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
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

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
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

	public void setSumgrades(final Double sumgrades) {
		this.sumgrades = sumgrades;
	}

	public Double getSumgrades() {
		return this.sumgrades;
	}
}
