package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class PlatformGroupSpecification  implements IClixMappingClass{
	
	private Long group;
	private Long person;
	private PlatformGroupSpecificationPK id;
	
	
	
	public PlatformGroupSpecificationPK getId() {
		return id;
	}
	
	public String getString()
	{
		return "PlatformGroupSpecification$$$"
				+this.getGroup()+"$$$"+
				this.getPerson();
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
