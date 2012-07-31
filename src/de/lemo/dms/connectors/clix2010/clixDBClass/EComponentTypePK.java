package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class EComponentTypePK implements Serializable{

	private Long component;
	private Long language;

	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof EComponentTypePK))
			return false;
		EComponentTypePK a = (EComponentTypePK)arg;
		if(a.getComponent() != this.component)
			return false;
		if(a.getLanguage() != this.language)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int hc;
		hc = language.hashCode();
		hc = 17 * hc + component.hashCode();
		return hc;
	}
	
	public long getLanguage() {
		return language;
	}

	public void setLanguage(long language) {
		this.language = language;
	}

	public EComponentTypePK()
	{
		
	}

	public long getComponent() {
		return component;
	}

	public void setComponent(long component) {
		this.component = component;
	}

}
