package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class PlatformGroup  implements IClixMappingClass{
	
	private Long id;
	private Long typeId;
	private String lastUpdated;
	private String created;
	
	

	public Long getId() {
		return id;
	}
	
	public String getString()
	{
		return "PlatformGroup$$$"
				+this.id+"$$$"
				+this.getCreated()+"$$$"
				+this.getLastUpdated()+"$$$"
				+this.getTypeId();
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getTypeId() {
		return typeId;
	}



	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}



	public String getLastUpdated() {
		return lastUpdated;
	}



	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



	public String getCreated() {
		return created;
	}



	public void setCreated(String created) {
		this.created = created;
	}



	public PlatformGroup()
	{
		
	}

	
}
