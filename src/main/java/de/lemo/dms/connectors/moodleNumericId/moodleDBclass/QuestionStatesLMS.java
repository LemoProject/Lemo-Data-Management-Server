/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Question_states_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

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
