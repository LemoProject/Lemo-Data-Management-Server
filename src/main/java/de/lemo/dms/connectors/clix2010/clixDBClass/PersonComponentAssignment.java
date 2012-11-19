package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class PersonComponentAssignment implements IClixMappingClass{
	
	private PersonComponentAssignmentPK id;
	private Long component;
	private Long person;
	private Long context;
	private String firstEntered;

	public String getFirstEntered() {
		return firstEntered;
	}

	public void setFirstEntered(String firstEntered) {
		this.firstEntered = firstEntered;
	}

	public PersonComponentAssignmentPK getId() {
		return id;
	}

	public void setId(PersonComponentAssignmentPK id) {
		this.id = id;
	}

	public Long getComponent() {
		return component;
	}

	public void setComponent(Long component) {
		this.component = component;
	}

	public Long getPerson() {
		return person;
	}

	public void setPerson(Long person) {
		this.person = person;
	}

	public Long getContext() {
		return context;
	}

	public void setContext(Long context) {
		this.context = context;
	}

	@Override
	public String getString() {
		return "PersonComponentAssignment$$$"
				+this.getComponent()+"$$$"
				+this.getPerson()+"$$$"
				+this.getContext();
	}
	
	public PersonComponentAssignment()
	{
		
	}
	
	
	

}
