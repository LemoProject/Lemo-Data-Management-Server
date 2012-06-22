package de.lemo.dms.connectors.clix2010.clixDBClass;

public class PortfolioLog {
	
	private long id;
	private long component;
	private long person;
	private long typeOfModification;
	private String lastUpdated;
	private long lastUpdater;
	
	
	
	public PortfolioLog()
	{
		
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
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



	public long getTypeOfModification() {
		return typeOfModification;
	}



	public void setTypeOfModification(long typeOfModification) {
		this.typeOfModification = typeOfModification;
	}



	public String getLastupdated() {
		return lastUpdated;
	}



	public void setLastupdated(String lastupdated) {
		this.lastUpdated = lastupdated;
	}



	public long getLastUpdater() {
		return lastUpdater;
	}



	public void setLastUpdater(long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	
	
	
}
