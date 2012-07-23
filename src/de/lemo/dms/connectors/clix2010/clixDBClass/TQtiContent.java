package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TQtiContent  implements IClixMappingClass{
	
	private Long id;
	private String created;
	private String lastUpdated;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getString()
	{
		return "TQtiContentä$"
				+this.id+"ä$"
				+this.getCreated()+"ä$"
				+this.getLastUpdated()+"ä$"
				+this.getName();
	}

	public TQtiContent()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	
	

}
