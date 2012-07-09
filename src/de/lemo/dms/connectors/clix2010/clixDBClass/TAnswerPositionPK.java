package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class TAnswerPositionPK implements Serializable {

	private Long person;
	private Long question;
	private Long task;
	private Long test;
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof TAnswerPositionPK))
			return false;
		TAnswerPositionPK a = (TAnswerPositionPK)arg;
		if(a.getPerson() != this.person)
			return false;
		if(a.getQuestion() != this.question)
			return false;
		if(a.getTask() != this.task)
			return false;
		if(a.getTest() != this.test)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		return person.hashCode() * 17 + question.hashCode() * 19 + task.hashCode() * 23 + test.hashCode() * 29;
	}
		
	public Long getPerson() {
		return person;
	}


	public void setPerson(Long person) {
		this.person = person;
	}


	public Long getQuestion() {
		return question;
	}


	public void setQuestion(Long question) {
		this.question = question;
	}


	public Long getTask() {
		return task;
	}


	public void setTask(Long task) {
		this.task = task;
	}


	public Long getTest() {
		return test;
	}


	public void setTest(Long test) {
		this.test = test;
	}



	public TAnswerPositionPK()
	{
	}
}
