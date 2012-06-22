package de.lemo.dms.connectors.clix2010.clixDBClass;

public class PlatformGroup {
	
	private long id;
	private long typeId;
	private String lastUpdated;
	private String created;
	
	public String getCerated() {
		return created;
	}

	public void setCerated(String created) {
		this.created = created;
	}

	public PlatformGroup()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	
	

}
