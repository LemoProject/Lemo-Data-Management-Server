package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class ScormSessionTimesPK implements Serializable {

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

	public long getComponent() {
		return component;
	}

	public void setComponent(long component) {
		this.component = component;
	}

	public long getPerson() {
		return person;
	}

	public void setPerson(long person) {
		this.person = person;
	}
	
}
