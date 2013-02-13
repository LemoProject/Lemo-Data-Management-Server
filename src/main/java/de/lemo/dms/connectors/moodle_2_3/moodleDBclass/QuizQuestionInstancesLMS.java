/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_question_instances_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class QuizQuestionInstancesLMS {

	public long id;
	public long quiz;
	public long question;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getQuiz() {
		return this.quiz;
	}

	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	public long getQuestion() {
		return this.question;
	}

	public void setQuestion(final long question) {
		this.question = question;
	}

}
