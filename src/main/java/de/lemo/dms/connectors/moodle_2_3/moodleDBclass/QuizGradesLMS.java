/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_grades_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table QuizGrades.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class QuizGradesLMS {

	private long id;
	private long userid;
	private long quiz;
	private double grade;
	private long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
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
