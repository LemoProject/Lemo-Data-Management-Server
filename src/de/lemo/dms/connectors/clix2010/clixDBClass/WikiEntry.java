package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class WikiEntry  implements IClixMappingClass{
	
	private Long id;
	private Long component;
	private Long creator;
	private Long lastProcessor;
	private Long publisher;
	private String lastUpdated;
	private String publishingDate;
	private String created;
	
	
	public String getCreated() {
		return created;
	}
	
	public String getString()
	{
		return "WikiEntryä$"
				+this.id+"ä$"
				+this.getCreated()+"ä$"
				+this.getLastUpdated()+"ä$"
				+this.getPublishingDate()+"ä$"
				+this.getComponent()+"ä$"
				+this.getCreator()+"ä$"
				+this.getLastProcessor()+"ä$"
				+this.getPublisher();
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public WikiEntry()
	{
		
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

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getLastProcessor() {
		return lastProcessor;
	}

	public void setLastProcessor(Long lastProcessor) {
		this.lastProcessor = lastProcessor;
	}

	public Long getPublisher() {
		return publisher;
	}

	public void setPublisher(Long publisher) {
		this.publisher = publisher;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(String publishingDate) {
		this.publishingDate = publishingDate;
	}

	
	
	
}
