package de.lemo.dms.connectors.clix2010.clixDBClass;

public class T2Task {
	
	private long id;
	private String questionText;
	private long taskType;
	private long topicId;
	private long inputType;
	
	public T2Task()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public long getTaskTypeId() {
		return taskType;
	}

	public void setTaskTypeId(long taskTypeId) {
		this.taskType = taskTypeId;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public long getInputType() {
		return inputType;
	}

	public void setInputType(long inputType) {
		this.inputType = inputType;
	}
	
	

}
