package de.lemo.dms.connectors.moodle.moodleDBclass;

public class Question_states_LMS {

	private long id;
	private long attempt;
	private long question;
	private String answer;
	private short event;
	private double penalty;
	private double raw_grade;
	private double grade;
	private long timestamp;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAttempt() {
		return attempt;
	}
	public void setAttempt(long attempt) {
		this.attempt = attempt;
	}
	public long getQuestion() {
		return question;
	}
	public void setQuestion(long question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public short getEvent() {
		return event;
	}
	public void setEvent(short event) {
		this.event = event;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	public double getRaw_grade() {
		return raw_grade;
	}
	public void setRaw_grade(double rawGrade) {
		raw_grade = rawGrade;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
