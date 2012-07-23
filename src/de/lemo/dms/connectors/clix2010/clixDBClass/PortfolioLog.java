package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class PortfolioLog  implements IClixMappingClass{
	
	private Long id;
	private Long component;
	private Long person;
	private Long typeOfModification;
	private String lastUpdated;
	private Long lastUpdater;
	
	
	
	public PortfolioLog()
	{
		
	}

	public String getString()
	{
		return "PortfolioLogä$"
				+this.id+"ä$"
					+this.getLastUpdated()+"ä$"
					+this.getComponent()+"ä$"
					+this.getLastUpdater()+"ä$"
					+this.getPerson()+"ä$"
					+this.getTypeOfModification();
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
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



	public Long getTypeOfModification() {
		return typeOfModification;
	}



	public void setTypeOfModification(Long typeOfModification) {
		this.typeOfModification = typeOfModification;
	}



	public String getLastUpdated() {
		return lastUpdated;
	}



	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



	public Long getLastUpdater() {
		return lastUpdater;
	}



	public void setLastUpdater(Long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

}
