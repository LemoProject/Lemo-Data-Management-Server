package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class ScormSessionTimesPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333399944075685332L;
	private Long component;
	private Long person;
	
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof ScormSessionTimesPK))
			return false;
		ScormSessionTimesPK a = (ScormSessionTimesPK)arg;
		if(a.getComponent() != this.component)
			return false;
		if(a.getPerson() != this.person)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int hc;
		hc = person.hashCode();
		hc = 17 * hc + component.hashCode();
		return hc;
	}
	
	public ScormSessionTimesPK()
	{
		
	}
	
	public ScormSessionTimesPK(Long component, long person)
	{
		this.component = component;
		this.person = person;
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
	
}
