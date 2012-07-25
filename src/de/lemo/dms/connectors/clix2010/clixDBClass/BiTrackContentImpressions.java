package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class BiTrackContentImpressions implements IClixMappingClass{
	
	private BiTrackContentImpressionsPK id;

	private Long content;
	private String dayOfAccess;
	private Long container;
	private Long user;
	private Long totalImpressions;
	private Long characteristic;
	
	
	public BiTrackContentImpressionsPK getId() {
		return id;
	}
	
	public String getString()
	{
		return "BiTrackContentImpressionsä$"
				+this.getContainer()+"ä$"
				+this.getDayOfAccess()+"ä$"
				+this.getCharacteristic()+"ä$"
				+this.getContent()+"ä$"
				+this.getTotalImpressions()+"ä$"
				+this.getUser();
	}

	public void setId(BiTrackContentImpressionsPK id) {
		this.id = id;
	}

	public Long getContent() {
		return content;
	}

	public void setContent(Long content) {
		this.content = content;
	}

	public Long getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(Long characteristic) {
		this.characteristic = characteristic;
	}

	public BiTrackContentImpressions()
	{
		
	}

	public String getDayOfAccess() {
		return dayOfAccess;
	}

	public void setDayOfAccess(String dayOfAccess) {
		this.dayOfAccess = dayOfAccess;
	}

	public long getContainer() {
		return container;
	}

	public void setContainer(Long container) {
		this.container = container;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getTotalImpressions() {
		return totalImpressions;
	}

	public void setTotalImpressions(long totalImpressions) {
		this.totalImpressions = totalImpressions;
	}
	
	

}
