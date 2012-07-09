package de.lemo.dms.connectors.clix2010.clixDBClass;

public class PlatformGroupSpecification {
	
	private Long group;
	private Long person;
	private PlatformGroupSpecificationPK id;
	
	
	
	public PlatformGroupSpecificationPK getId() {
		return id;
	}



	public void setId(PlatformGroupSpecificationPK id) {
		this.id = id;
	}



	public Long getGroup() {
		return group;
	}



	public void setGroup(Long group) {
		this.group = group;
	}



	public Long getPerson() {
		return person;
	}



	public void setPerson(Long person) {
		this.person = person;
	}



	public PlatformGroupSpecification()
	{
		
	}

}
