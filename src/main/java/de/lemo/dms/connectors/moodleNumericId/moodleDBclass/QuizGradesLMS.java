/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_grades_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table QuizGrades.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class QuizGradesLMS {

	private long id;
	private String userid;
	private long quiz;
	private double grade;
	private long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public double getGrade() {
		return this.grade;
	}

	public void setGrade(final double grade) {
		this.grade = grade;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	public long getQuiz() {
		return this.quiz;
	}
}
