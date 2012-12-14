package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class PersonComponentAssignmentPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245182973491294243L;
	private Long person;
	private Long component;
	private Long context;
	
	public Long getPerson() {
		return person;
	}

	public void setPerson(Long person) {
		this.person = person;
	}

	public Long getComponent() {
		return component;
	}

	public void setComponent(Long component) {
		this.component = component;
	}

	public Long getContext() {
		return context;
	}

	public void setContext(Long context) {
		this.context = context;
	}

	public PersonComponentAssignmentPK()
	{
		
		
	}

	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof PersonComponentAssignmentPK))
			return false;
		PersonComponentAssignmentPK a = (PersonComponentAssignmentPK)arg;
		if(a.getComponent() != this.component)
			return false;
		if(a.getPerson() != this.person)
			return false;
		if(a.getContext() != this.context)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		return person.hashCode() * 17 + component.hashCode() * 19 + context.hashCode() * 23;
	}
	
	
	
}
