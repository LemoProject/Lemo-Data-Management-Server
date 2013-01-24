/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/QuizQuestionMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the relationship between quiz and questions. */
public class QuizQuestionMining implements IMappingClass {

	private long id;
	private QuizMining quiz;
	private QuestionMining question;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof QuizQuestionMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof QuizQuestionMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between quiz and question
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between quiz and question
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute quiz
	 * 
	 * @return the quiz which is associated
	 */
	public QuizMining getQuiz() {
		return this.quiz;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the quiz which is associated
	 */
	public void setQuiz(final QuizMining quiz) {
		this.quiz = quiz;
	}

	/**
	 * parameterized setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the id of the quiz which is associated
	 * @param quizMining
	 *            a list of new added quiz, which is searched for the quiz with the id submitted in the quiz parameter
	 * @param oldQuizMining
	 *            a list of quiz in the miningdatabase, which is searched for the quiz with the id submitted in the quiz
	 *            parameter
	 */
	public void setQuiz(final long quiz, final Map<Long, QuizMining> quizMining,
			final Map<Long, QuizMining> oldQuizMining) {

		if (quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuizQuestion(this);
		}
		if ((this.quiz == null) && (oldQuizMining.get(quiz) != null))
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuizQuestion(this);
		}
	}

	/**
	 * standard getter for the attribute question
	 * 
	 * @return the question which is associated
	 */
	public QuestionMining getQuestion() {
		return this.question;
	}

	/**
	 * standard setter for the attribute question
	 * 
	 * @param question
	 *            the question which is associated
	 */
	public void setQuestion(final QuestionMining question) {
		this.question = question;
	}

	/**
	 * parameterized setter for the attribute question
	 * 
	 * @param question
	 *            the quiz which is associated
	 * @param questionMining
	 *            a list of new added question, which is searched for the question with the id submitted in the question
	 *            parameter
	 * @param oldQuestionMining
	 *            a list of question in the miningdatabase, which is searched for the question with the id submitted in
	 *            the question parameter
	 */
	public void setQuestion(final long question, final Map<Long, QuestionMining> questionMining,
			final Map<Long, QuestionMining> oldQuestionMining) {

		if (questionMining.get(question) != null)
		{
			this.question = questionMining.get(question);
			questionMining.get(question).addQuizQuestion(this);
		}
		if ((this.question == null) && (oldQuestionMining.get(question) != null))
		{
			this.question = oldQuestionMining.get(question);
			oldQuestionMining.get(question).addQuizQuestion(this);
		}
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
