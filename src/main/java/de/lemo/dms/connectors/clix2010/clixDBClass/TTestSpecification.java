/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TTestSpecification.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TTestSpecification implements IClixMappingClass {

	private TTestSpecificationPK id;
	private Long task;
	private Long test;

	public TTestSpecificationPK getId() {
		return this.id;
	}

	public String getString()
	{
		return "TTestSpecification$$$"
				+ this.getTask() + "$$$"
				+ this.getTest();
	}

	public void setId(final TTestSpecificationPK id) {
		this.id = id;
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

	public TTestSpecification() {
	}

}
