package de.lemo.dms.connectors.moodle.moodleDBclass;

public class Quiz_grades_LMS {

	private long id;
	private String userid;
	private long quiz;
	private double grade;
	private long timemodified;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public void setQuiz(long quiz) {
		this.quiz = quiz;
	}
	public long getQuiz() {
		return quiz;
	}
}
