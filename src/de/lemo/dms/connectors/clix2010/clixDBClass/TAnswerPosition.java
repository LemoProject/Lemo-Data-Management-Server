package de.lemo.dms.connectors.clix2010.clixDBClass;

public class TAnswerPosition {

	private TAnswerPositionPK id;
	private long person;
	private long question;
	private long task;
	private long test;
	
	public TAnswerPositionPK getId() {
		return id;
	}


	public void setId(TAnswerPositionPK id) {
		this.id = id;
	}


	private String evaluated;
	
	
	public long getPerson() {
		return person;
	}


	public void setPerson(long person) {
		this.person = person;
	}


	public long getQuestion() {
		return question;
	}


	public void setQuestion(long question) {
		this.question = question;
	}


	public long getTask() {
		return task;
	}


	public void setTask(long task) {
		this.task = task;
	}


	public long getTest() {
		return test;
	}


	public void setTest(long test) {
		this.test = test;
	}


	public String getEvaluated() {
		return evaluated;
	}


	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}


	public TAnswerPosition()
	{
	}
}
