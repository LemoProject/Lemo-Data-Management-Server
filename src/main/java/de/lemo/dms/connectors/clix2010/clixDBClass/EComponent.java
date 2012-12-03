package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class EComponent  implements IClixMappingClass{

	
	private Long id;
	private Long type;
	private String name;
	private String creationDate;
	private String lastUpdated;
	private String startDate;
	private String description;

	public EComponent()
	{
		
	}
	
	public String getString()
	{
		return "EComponent$$$"
				+this.id+"$$$"
				+this.getCreationDate()+"$$$"
				+this.getDescription()+"$$$"
				+this.getLastUpdated()+"$$$"
				+this.getName()+"$$$"
				+this.getStartDate()+"$$$"
				+this.getType();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getType() {
		return type;
	}
	
	public void setType(Long type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
