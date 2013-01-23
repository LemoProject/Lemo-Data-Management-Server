package de.lemo.dms.connectors.moodle.moodleDBclass;

public class QuestionAnswersLMS {

	private long id;
	private long question;
	private String answer;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
}
