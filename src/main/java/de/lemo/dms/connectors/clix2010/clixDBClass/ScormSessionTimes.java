package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class ScormSessionTimes  implements IClixMappingClass{

	private ScormSessionTimesPK id;

	private Long component;
	private Long person;
	private String score;
	private String lastUpdated;
	private String status;
	
	public ScormSessionTimesPK getId() {
		return id;
	}
	
	public String getString()
	{
		return "ScormSessionTimes$$$"
				+this.getLastUpdated()+"$$$"
				+this.getScore()+"$$$"
				+this.getStatus()+"$$$"
				+this.getComponent()+"$$$"
				+this.getPerson();
	}

	public void setId(ScormSessionTimesPK id) {
		this.id = id;
	}

	public ScormSessionTimes()
	{
		
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
