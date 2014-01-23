/**
 * File ./src/main/java/de/lemo/dms/db/mapping/QuizUserMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/QuizUserMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.IMappingClass;
import de.lemo.dms.db.mapping.abstractions.IRatedUserAssociation;

/** This class represents the association between the quiz and the user. */
@Entity
@Table(name = "quiz_user")
public class QuizUserMining implements IMappingClass, IRatedUserAssociation {

	private long id;
	private UserMining user;
	private CourseMining course;
	private QuizMining quiz;
	private double rawGrade;
	private double finalGrade;
	private long timemodified;
	private Long platform;

	@Override
	@Transient
	public Long getPrefix() {
		return 14L;
	}
	
	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof QuizUserMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof QuizUserMining)) {
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
	 * @return the identifier for the association between quiz and user
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
	 *            the identifier for the association between quiz and user
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who is associated
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is associated
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the id of the user who is associated
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
			userMining.get(user).addQuizUser(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addQuizUser(this);
		}

	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")	 
	public CourseMining getCourse() {
		return this.course;
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
			courseMining.get(course).addQuizUser(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addQuizUser(this);
		}
	}

	/**
	 * standard getter for the attribute quiz
	 * 
	 * @return the quiz in which the action takes place
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="quiz_id")
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
	 * @param id
	 *            the id of the quiz in which the action takes place
	 * @param quizMining
	 *            a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other
	 *            parameters
	 * @param oldQuizMining
	 *            a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted
	 *            in the other parameters
	 */
	public void setQuiz(final long quiz, final Map<Long, QuizMining> quizMining,
			final Map<Long, QuizMining> oldQuizMining) {

		if (quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuizUser(this);
		}
		if ((this.quiz == null) && (oldQuizMining.get(quiz) != null))
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuizUser(this);
		}
	}

	/**
	 * standard getter for the attribute rawgrade
	 * 
	 * @return the raw grade of the user in this exercise
	 */
	@Column	(name="rawgrade")
	public double getRawGrade() {
		return this.rawGrade;
	}

	/**
	 * standard setter for the attribute rawgrade
	 * 
	 * @param rawGrade
	 *            the raw grade of the user in this quiz
	 */
	public void setRawGrade(final double rawGrade) {
		this.rawGrade = rawGrade;
	}

	/**
	 * standard getter for the attribute finalgrade
	 * 
	 * @return the final grade of the user in this quiz
	 */
	@Column	(name="finalgrade")
	public double getFinalGrade() {
		return this.finalGrade;
	}

	/**
	 * standard setter for the attribute finalgrade
	 * 
	 * @param finalGrade
	 *            the final grade of the user in this quiz
	 */
	public void setFinalGrade(final double finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the grade was changed the last time
	 */
	@Column	(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the grade was changed the last time
	 */
	public void setTimemodified(final long timeModified) {
		this.timemodified = timeModified;
	}

	@Column	(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	@Transient
	public Long getLearnObjId() {
		return this.getQuiz().getId();
	}

	@Override
	@Transient
	public Double getMaxGrade() {
		return this.getQuiz().getMaxGrade();
	}
}
