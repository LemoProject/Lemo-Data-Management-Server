/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/QuestionMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table question. */
public class QuestionMining implements IMappingClass, ILearningObject {

	private long id;
	private String title;
	private String text;
	private String type;
	private long timeCreated;
	private long timeModified;
	private Long platform;

	private Set<QuizQuestionMining> quizQuestions = new HashSet<QuizQuestionMining>();
	private Set<QuestionLogMining> questionLogs = new HashSet<QuestionLogMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof QuestionMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof QuestionMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the question
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the question
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the question
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the question
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute text
	 * 
	 * @return the text of the question
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * standard setter for the attribute text
	 * 
	 * @param text
	 *            the text of the question
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * standard getter for the attribute type
	 * 
	 * @return the type of the question
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute type
	 * 
	 * @param type
	 *            the type of the question
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the question was created
	 */
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timecreated
	 *            the timestamp when the question was created
	 */
	public void setTimeCreated(final long timecreated) {
		this.timeCreated = timecreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the question was changed the last time
	 */
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the question was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute quiz_question
	 * 
	 * @param quizQuestion
	 *            a set of entries in the quiz_question table which which relate the quiz to questions
	 */
	public void setQuizQuestions(final Set<QuizQuestionMining> quizQuestion) {
		this.quizQuestions = quizQuestion;
	}

	/**
	 * standard getter for the attribute quiz_question
	 * 
	 * @return a set of entries in the quiz_question table which which relate the quiz to questions
	 */
	public Set<QuizQuestionMining> getQuizQuestions() {
		return this.quizQuestions;
	}

	/**
	 * standard setter for the attribute quiz_question
	 * 
	 * @param quizQuestion
	 *            this entry will be added to the list of quiz_question in this question
	 */
	public void addQuizQuestion(final QuizQuestionMining quizQuestion) {
		this.quizQuestions.add(quizQuestion);
	}

	/**
	 * standard setter for the attribute question_log
	 * 
	 * @param questionLog
	 *            a set of entries in the question_log table which are related with the question
	 */
	public void setQuestionLogs(final Set<QuestionLogMining> questionLog) {
		this.questionLogs = questionLog;
	}

	/**
	 * standard getter for the attribute question_log
	 * 
	 * @return a set of entries in the question_log table which are related with the question
	 */
	public Set<QuestionLogMining> getQuestionLogs() {
		return this.questionLogs;
	}

	/**
	 * standard setter for the attribute question_log
	 * 
	 * @param questionLog
	 *            this entry will be added to the list of question_log in this question
	 */
	public void addQuestionLog(final QuestionLogMining questionLog) {
		this.questionLogs.add(questionLog);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
