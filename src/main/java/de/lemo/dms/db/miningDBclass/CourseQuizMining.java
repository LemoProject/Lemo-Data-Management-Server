/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseQuizMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/** This class represents the relationship between the courses and quiz. */
public class CourseQuizMining implements IMappingClass, ICourseRatedObjectAssociation {

	// implements Serializable
	private long id;
	private CourseMining course;
	private QuizMining quiz;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseQuizMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseQuizMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return a course in which the quiz is used
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}
	
	@Override
	public Long getPrefix() {
		return this.quiz.getPrefix();
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the quiz is used
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the quiz is used
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
			courseMining.get(course).addCourseQuiz(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseQuiz(this);
		}
	}

	/**
	 * standard getter for the attribute quiz
	 * 
	 * @return the quiz which is used in the course
	 */
	public QuizMining getQuiz() {
		return this.quiz;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the quiz which is used in the course
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
			quizMining.get(quiz).addCourseQuiz(this);
		}
		if ((this.quiz == null) && (oldQuizMining.get(quiz) != null))
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addCourseQuiz(this);
		}
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and quiz
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and quiz
	 */
	@Override
	public long getId() {
		return this.id;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	public IRatedObject getRatedObject() {
		return this.quiz;
	}
}
