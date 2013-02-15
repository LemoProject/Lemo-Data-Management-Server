/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TAnswerPositionPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

/**
 * Class for realization of primary key for TAnswerPosition.
 * 
 * @author S.Schwarzrock
 *
 */
public class TAnswerPositionPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4802836843072674394L;
	private Long person;
	private Long question;
	private Long task;
	private Long test;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof TAnswerPositionPK)) {
			return false;
		}
		final TAnswerPositionPK a = (TAnswerPositionPK) arg;
		if (a.getPerson() != this.person) {
			return false;
		}
		if (a.getQuestion() != this.question) {
			return false;
		}
		if (a.getTask() != this.task) {
			return false;
		}
		if (a.getTest() != this.test) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.person.hashCode() * 17) + (this.question.hashCode() * 19) + (this.task.hashCode() * 23)
				+ (this.test.hashCode() * 29);
	}

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

	public TAnswerPositionPK()
	{
	}
}
