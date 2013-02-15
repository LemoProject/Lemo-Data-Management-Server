/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/QuestionAnswersLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table QuestionAnswers.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class QuestionAnswersLMS {

	private long id;
	private long question;
	private String answer;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
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
}
