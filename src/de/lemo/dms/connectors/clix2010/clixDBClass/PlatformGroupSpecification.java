package de.lemo.dms.connectors.clix2010.clixDBClass;

public class PlatformGroupSpecification {
	
	private long group;
	private long person;
	private PlatformGroupSpecificationPK id;
	
	
	
	public PlatformGroupSpecificationPK getId() {
		return id;
	}



	public void setId(PlatformGroupSpecificationPK id) {
		this.id = id;
	}



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



	public PlatformGroupSpecification()
	{
		
	}

}
