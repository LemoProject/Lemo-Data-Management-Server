/**
 * File ./src/main/java/de/lemo/dms/db/mapping/QuizMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/QuizMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

/** This class represents the table quiz. */
@SuppressWarnings("serial")
@Entity
@Table(name = "quiz")
public class QuizMining implements Serializable, IMappingClass, ILearningObject, IRatedObject {

	private long id;
	private String type;
	private String title;
	private double maxGrade;
	private long timeOpen;
	private long timeClose;
	private Long platform;
	private long timeCreated;
	private long timeModified;


	private Set<CourseQuizMining> courseQuizzes = new HashSet<CourseQuizMining>();
	private Set<QuizQuestionMining> quizQuestions = new HashSet<QuizQuestionMining>();
	private Set<QuizLogMining> quizLogs = new HashSet<QuizLogMining>();
	private Set<QuestionLogMining> questionLogs = new HashSet<QuestionLogMining>();
	private Set<QuizUserMining> quizUsers = new HashSet<QuizUserMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof QuizMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof QuizMining)) {
			return true;
		}
		return false;
	}

	@Column(name="type")
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof QuizMining)) {
			return false;
		}
		final QuizMining q = (QuizMining) obj;
		if ((q.id == this.id) && (q.timeModified == this.timeModified)) {
			return true;
		}
		return false;
	}

	private Integer hashcodeValue = null;

	@Override
	public synchronized int hashCode() {
		if (this.hashcodeValue == null) {
			this.hashcodeValue = 42 + Long.bitCount(this.id) + Long.bitCount(this.timeModified);
			return this.hashcodeValue;
		}
		return this.hashcodeValue.intValue();
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the quiz will be accessible after by students
	 */
	@Column(name="timeopen")
	public long getTimeOpen() {
		return this.timeOpen;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timeopen
	 *            the timestamp the quiz will be accessible after by students
	 */
	public void setTimeOpen(final long timeopen) {
		this.timeOpen = timeopen;
	}

	/**
	 * standard getter for the attribute timeClose
	 * 
	 * @return the timestamp after that the quiz will be not accessible any more by students
	 */
	@Column(name="timeclose")
	public long getTimeClose() {
		return this.timeClose;
	}

	/**
	 * standard setter for the attribute timeClose
	 * 
	 * @param timeClose
	 *            the timestamp after that the quiz will be not accessible any more by students
	 */
	public void setTimeClose(final long timeClose) {
		this.timeClose = timeClose;
	}

	/**
	 * standard getter for the attribute timeCreated
	 * 
	 * @return the timestamp when the quiz was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timeCreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the quiz was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timeModified
	 * 
	 * @return the timestamp when the quiz was changed the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timeModified
	 * 
	 * @param timeModified
	 *            the timestamp when the quiz was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the quiz
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the quiz
	 */
	@Override
	@Lob
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute course_quiz
	 * 
	 * @param courseQuizzes
	 *            a set of entries in the course_quiz table which relate the quiz to courses
	 */
	public void setCourseQuizzes(final Set<CourseQuizMining> courseQuizzes) {
		this.courseQuizzes = courseQuizzes;
	}

	/**
	 * standard getter for the attribute course_quiz
	 * 
	 * @return a set of entries in the course_quiz association which which relate the quiz to courses
	 */
	@OneToMany(mappedBy="quiz")
	public Set<CourseQuizMining> getCourseQuizzes() {
		return this.courseQuizzes;
	}

	/**
	 * standard add method for the attribute course_quiz
	 * 
	 * @param courseQuiz
	 *            this entry will be added to the list of course_quiz in this quiz
	 */
	public void addCourseQuiz(final CourseQuizMining courseQuiz) {
		this.courseQuizzes.add(courseQuiz);
	}

	/**
	 * standard setter for the attribute quiz_question
	 * 
	 * @param quizQuestions
	 *            a set of entries in the quiz_question which relate the quiz to questions
	 */
	public void setQuizQuestions(final Set<QuizQuestionMining> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}

	/**
	 * standard getter for the attribute quiz_question
	 * 
	 * @return a set of entries in the quiz_question table which which relate the quiz to questions
	 */
	@OneToMany(mappedBy="quiz")
	public Set<QuizQuestionMining> getQuizQuestions() {
		return this.quizQuestions;
	}

	/**
	 * standard add method for the attribute quiz_question
	 * 
	 * @param quizQuestion
	 *            this entry will be added to the list of quiz_question in this quiz
	 */
	public void addQuizQuestion(final QuizQuestionMining quizQuestion) {
		this.quizQuestions.add(quizQuestion);
	}

	/**
	 * standard setter for the attribute quiz_log
	 * 
	 * @param quizLogs
	 *            a set of entries in the quiz_log table which are related with this quiz
	 */
	public void setQuizLogs(final Set<QuizLogMining> quizLogs) {
		this.quizLogs = quizLogs;
	}

	/**
	 * standard getter for the attribute quiz_log
	 * 
	 * @return a set of entries in the quiz_log table which are related with this quiz
	 */
	@OneToMany(mappedBy="quiz")
	public Set<QuizLogMining> getQuizLogs() {
		return this.quizLogs;
	}

	/**
	 * standard setter for the attribute quiz_log
	 * 
	 * @param quizLog
	 *            this entry will be added to the list of quiz_log in this quiz
	 */
	public void addQuizLog(final QuizLogMining quizLog) {
		this.quizLogs.add(quizLog);
	}

	/**
	 * standard setter for the attribute question_log
	 * 
	 * @param questionLogs
	 *            a set of entries in the question_log table which are related with the questions used in this quiz
	 */
	public void setQuestionLogs(final Set<QuestionLogMining> questionLogs) {
		this.questionLogs = questionLogs;
	}

	/**
	 * standard getter for the attribute question_log
	 * 
	 * @return a set of entries in the question_log table which are related with the questions used in this quiz
	 */
	@OneToMany(mappedBy="quiz")
	public Set<QuestionLogMining> getQuestionLogs() {
		return this.questionLogs;
	}

	/**
	 * standard add method for the attribute question_log
	 * 
	 * @param questionLog
	 *            this entry will be added to the list of question_log in this quiz
	 */
	public void addQuestionLog(final QuestionLogMining questionLog) {
		this.questionLogs.add(questionLog);
	}

	/**
	 * standard setter for the attribute qtype
	 * 
	 * @param qtype
	 *            type of this quiz
	 */
	public void setQtype(final String qtype) {
		this.type = qtype;
	}

	/**
	 * standard getter for the attribute qtype
	 * 
	 * @return the type of this quiz
	 */
	@Transient
	public String getQtype() {
		return this.type;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the identifier for this quiz
	 */
	public void setId(final long quiz) {
		this.id = quiz;
	}

	/**
	 * standard getter for the attribute qid
	 * 
	 * @return the identifier for this quiz
	 */
	@Override
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard getter for the attribute maxGrade
	 * 
	 * @return the maximum grade which is set for the quiz
	 */
	@Override
	@Column(name="maxgrade")
	public Double getMaxGrade() {
		return this.maxGrade;
	}

	/**
	 * standard setter for the attribute maxGrade
	 * 
	 * @param maxGrade
	 *            the maximum grade which is set for the quiz
	 */
	public void setMaxGrade(final double maxGrade) {
		this.maxGrade = maxGrade;
	}

	/**
	 * standard setter for the attribute quiz_user
	 * 
	 * @param quizUsers
	 *            a set of entries in the quiz_user table which relate the quiz to user
	 */
	public void setQuizUsers(final Set<QuizUserMining> quizUsers) {
		this.quizUsers = quizUsers;
	}

	/**
	 * standard getter for the attribute quiz_user
	 * 
	 * @return a set of entries in the quiz_user table which relate the quiz to user
	 */
	@OneToMany(mappedBy="quiz")
	public Set<QuizUserMining> getQuizUsers() {
		return this.quizUsers;
	}

	/**
	 * standard add method for the attribute quiz_user
	 * 
	 * @param quizUser
	 *            this entry will be added to the list of quiz_user in this quiz
	 */
	public void addQuizUser(final QuizUserMining quizUser) {
		this.quizUsers.add(quizUser);
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	@Transient
	public Long getPrefix() {
		return 14L;
	}
}
