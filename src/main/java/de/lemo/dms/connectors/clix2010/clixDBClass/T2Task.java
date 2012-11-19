package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class T2Task  implements IClixMappingClass{
	
	private Long id;
	private String questionText;
	private Long taskType;
	private Long topicId;
	private Long inputType;
	
	public T2Task()
	{
		
	}

	public Long getId() {
		return id;
	}
	
	public String getString()
	{
		return "T2Task$$$"
				+this.getId()+"$$$"
				+this.getQuestionText()+"$$$"
				+this.getInputType()+"$$$"
				+this.getTaskType()+"$$$"
				+this.getTopicId();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Long getTaskType() {
		return taskType;
	}

	public void setTaskType(Long taskType) {
		this.taskType = taskType;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getInputType() {
		return inputType;
	}

	public void setInputType(Long inputType) {
		this.inputType = inputType;
	}

	

}
