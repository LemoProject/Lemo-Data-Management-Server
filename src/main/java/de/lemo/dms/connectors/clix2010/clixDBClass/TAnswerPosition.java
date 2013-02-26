/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TAnswerPosition.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TAnswerPosition.
 * 
 * @author S.Schwarzrock
 *
 */
public class TAnswerPosition implements IClixMappingClass {

	private TAnswerPositionPK id;
	private Long person;
	private Long question;
	private Long task;
	private Long test;

	public TAnswerPositionPK getId() {
		return this.id;
	}

	public String getString()
	{
		return "TAnswerPosition$$$"
				+ this.getEvaluated() + "$$$"
				+ this.getPerson() + "$$$"
				+ this.getQuestion() + "$$$"
				+ this.getTask() + "$$$"
				+ this.getTest();
	}

	public void setId(final TAnswerPositionPK id) {
		this.id = id;
	}

	private String evaluated;

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public Long getQuestion() {
		return this.question;
	}

	public void setQuestion(final Long question) {
		this.question = question;
	}

	public Long getTask() {
		return this.task;
	}

	public void setTask(final Long task) {
		this.task = task;
	}

	public Long getTest() {
		return this.test;
	}

	public void setTest(final Long test) {
		this.test = test;
	}

	public String getEvaluated() {
		return this.evaluated;
	}

	public void setEvaluated(final String evaluated) {
		this.evaluated = evaluated;
	}

	public TAnswerPosition()
	{
	}
}
