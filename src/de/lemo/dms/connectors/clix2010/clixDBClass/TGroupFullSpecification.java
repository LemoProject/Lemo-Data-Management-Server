package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TGroupFullSpecification  implements IClixMappingClass{
	
	private Long group;
	private Long person;
	
	
	
	public Long getGroup() {
		return group;
	}

	public String getString()
	{
		return "TGroupFullSpecificationä$"
				+this.getGroup()+"ä$"
				+this.getPerson();
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



	public TGroupFullSpecification()
	{
		
	}

}
