/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/T2Task.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class T2Task implements IClixMappingClass {

	private Long id;
	private String questionText;
	private Long taskType;
	private Long topicId;
	private Long inputType;

	public T2Task()
	{

	}

	public Long getId() {
		return this.id;
	}

	public String getString()
	{
		return "T2Task$$$"
				+ this.getId() + "$$$"
				+ this.getQuestionText() + "$$$"
				+ this.getInputType() + "$$$"
				+ this.getTaskType() + "$$$"
				+ this.getTopicId();
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return this.questionText;
	}

	public void setQuestionText(final String questionText) {
		this.questionText = questionText;
	}

	public Long getTaskType() {
		return this.taskType;
	}

	public void setTaskType(final Long taskType) {
		this.taskType = taskType;
	}

	public Long getTopicId() {
		return this.topicId;
	}

	public void setTopicId(final Long topicId) {
		this.topicId = topicId;
	}

	public Long getInputType() {
		return this.inputType;
	}

	public void setInputType(final Long inputType) {
		this.inputType = inputType;
	}

}
