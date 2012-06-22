package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class PlatformGroupSpecificationPK implements Serializable {
	
	private Long group;
	private Long person;
	
	
	
	public long getGroup() {
		return group;
	}



	public void setGroup(long group) {
		this.group = group;
	}



	public long getPerson() {
		return person;
	}



	public void setPerson(long person) {
		this.person = person;
	}



	public PlatformGroupSpecificationPK()
	{
		
	}
	
	public PlatformGroupSpecificationPK(Long person, Long group)
	{
		this.group = group;
		this.person = person;
	}
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof PlatformGroupSpecificationPK))
			return false;
		PlatformGroupSpecificationPK a = (PlatformGroupSpecificationPK)arg;
		if(a.getGroup() != this.group)
			return false;
		if(a.getPerson() != this.person)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int hc;
		hc = group.hashCode();
		hc = 17 * hc + person.hashCode();
		return hc;
	}


}
