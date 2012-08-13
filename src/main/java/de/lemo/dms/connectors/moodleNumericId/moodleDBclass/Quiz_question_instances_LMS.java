package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class Quiz_question_instances_LMS {

	public long id;
	public long quiz;
	public long question;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getQuiz() {
		return quiz;
	}
	public void setQuiz(long quiz) {
		this.quiz = quiz;
	}
	public long getQuestion() {
		return question;
	}
	public void setQuestion(long question) {
		this.question = question;
	}
	
}
