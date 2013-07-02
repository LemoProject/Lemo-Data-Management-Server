/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/QuestionLogMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/QuestionLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the log table for the question object. */
public class QuestionLogMining implements ILogMining, IMappingClass {

	private long id;
	private QuestionMining question;
	private UserMining user;
	private CourseMining course;
	private QuizMining quiz;
	private String type;
	private double penalty;
	private double rawGrade;
	private double finalGrade;
	private String answers;
	private long timestamp;
	private String action;
	private Long duration;
	private Long platform;

	@Override
	public int compareTo(final ILogMining arg0) {
		ILogMining s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.timestamp > s.getTimestamp()) {
				return 1;
			}
			if (this.timestamp < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (o != null) {} else return false;
		if (!(o instanceof QuestionLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof QuestionLogMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	@Override
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	@Override
	public String getTitle()
	{
		return this.question == null ? null : this.question.getTitle();
	}

	@Override
	public Long getLearnObjId()
	{
		return this.question == null ? null : this.question.getId();
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the log entry
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the log entry
	 */
	@Override
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of the course in which the action takes place
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {
		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addQuestionLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addQuestionLog(this);
		}
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
	 * standard getter for the attribute answers
	 * 
	 * @return the answers which are submittet with this action
	 */
	public String getAnswers() {
		return this.answers;
	}

	/**
	 * standard setter for the attribute answers
	 * 
	 * @param answers
	 *            the answers which are submittet with this action
	 */
	public void setAnswers(final String answers) {
		this.answers = answers;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the action did occur
	 */
	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp the action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribute penalty
	 * 
	 * @return the penalty on the grade in this action
	 */
	public double getPenalty() {
		return this.penalty;
	}

	/**
	 * standard setter for the attribute penalty
	 * 
	 * @param penalty
	 *            the penalty on the grade in this action
	 */
	public void setPenalty(final double penalty) {
		this.penalty = penalty;
	}

	/**
	 * standard getter for the attribute raw_grade
	 * 
	 * @return the raw grade in this action(grade without penalty)
	 */
	public double getRawGrade() {
		return this.rawGrade;
	}

	/**
	 * standard setter for the attribute raw_grade
	 * 
	 * @param rawGrade
	 *            the raw grade in this action(grade without penalty)
	 */
	public void setRawGrade(final Double rawGrade) {
		this.rawGrade = rawGrade;
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade in this action(with penalty)
	 */
	public Double getFinalGrade() {
		return this.finalGrade;
	}

	/**
	 * standard setter for the attribute finalgrade
	 * 
	 * @param finalGrade
	 *            the final grade in this action(with penalty)
	 */
	public void setFinalGrade(final Double finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * standard getter for the attribute question
	 * 
	 * @return the question with which is interacted
	 */
	public QuestionMining getQuestion() {
		return this.question;
	}

	/**
	 * standard setter for the attribute question
	 * 
	 * @param question
	 *            the question with which is interacted
	 */
	public void setQuestion(final QuestionMining question) {
		this.question = question;
	}

	/**
	 * parameterized setter for the attribute question
	 * 
	 * @param question
	 *            the id of the course in which the action takes place
	 * @param questionMining
	 *            a list of new added questions, which is searched for the question with the id submitted in the
	 *            question parameter
	 * @param oldQuestionMining
	 *            a list of questions in the miningdatabase, which is searched for the question with the id submitted in
	 *            the question parameter
	 */
	public void setQuestion(final long question, final Map<Long, QuestionMining> questionMining,
			final Map<Long, QuestionMining> oldQuestionMining) {

		if (questionMining.get(question) != null)
		{
			this.question = questionMining.get(question);
			questionMining.get(question).addQuestionLog(this);
		}
		if ((this.question == null) && (oldQuestionMining.get(question) != null))
		{
			this.question = oldQuestionMining.get(question);
			oldQuestionMining.get(question).addQuestionLog(this);
		}
	}

	/**
	 * standard getter for the attribute quiz
	 * 
	 * @return the quiz in which the action takes place
	 */
	public QuizMining getQuiz() {
		return this.quiz;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the quiz in which the action takes place
	 */
	public void setQuiz(final QuizMining quiz) {
		this.quiz = quiz;
	}

	/**
	 * parameterized setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the id of the quiz in which the action takes place
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
			quizMining.get(quiz).addQuestionLog(this);
		}
		if ((this.quiz == null) && (oldQuizMining.get(quiz) != null))
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuestionLog(this);
		}
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the question
	 */
	@Override
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the question
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the question
	 * @param userMining
	 *            a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining
	 *            a list of user in the miningdatabase, which is searched for the user with the id submitted in the user
	 *            parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {
		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addQuestionLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addQuestionLog(this);
		}
	}

	/**
	 * standard setter for the attribute action
	 * 
	 * @param action
	 *            the action which occur
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * standard getter for the attribute action
	 * 
	 * @return the action which occur
	 */
	@Override
	public String getAction() {
		return this.action;
	}

	@Override
	public Long getPrefix() {
		return 13L;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
