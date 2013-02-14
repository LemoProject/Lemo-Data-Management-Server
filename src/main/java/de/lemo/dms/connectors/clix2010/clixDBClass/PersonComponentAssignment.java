/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignment.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class PersonComponentAssignment implements IClixMappingClass {

	private PersonComponentAssignmentPK id;
	private Long component;
	private Long person;
	private Long context;
	private String firstEntered;

	public String getFirstEntered() {
		return this.firstEntered;
	}

	public void setFirstEntered(final String firstEntered) {
		this.firstEntered = firstEntered;
	}

	public PersonComponentAssignmentPK getId() {
		return this.id;
	}
	
	public Long getLongId()
	{
		return Long.valueOf(component + "" + person);
	}

	public void setId(final PersonComponentAssignmentPK id) {
		this.id = id;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public Long getContext() {
		return this.context;
	}

	public void setContext(final Long context) {
		this.context = context;
	}

	public PersonComponentAssignment()
	{

	}

}
