/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/QuestionMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/QuestionMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table question. */
@Entity
@Table(name = "question")
public class QuestionMining implements IMappingClass, ILearningObject {

	private long id;
	private String title;
	private String text;
	private String type;
	private long timeCreated;
	private long timeModified;
	private Long platform;
	private static final Long PREFIX = 13L;
	
	@Transient
	public Long getPrefix()
	{
		return PREFIX;
	}


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
	@Id
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
	@Column	(name="title", length=1000)
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
	@Column	(name="text", length=1000)
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
	@Column	(name="type")
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
	@Column	(name="timecreated")
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
	@Column	(name="timemodified")
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
	@OneToMany(mappedBy="question")
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
	@OneToMany(mappedBy="question")
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

	@Column	(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
