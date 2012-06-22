package de.lemo.dms.connectors.clix2010.clixDBClass;

public class WikiEntry {
	
	private long id;
	private long component;
	private long creator;
	private long lastProcessor;
	private long publisher;
	private String lastUpdated;
	private String publishingDate;
	
	public WikiEntry()
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

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public long getLastProcessor() {
		return lastProcessor;
	}

	public void setLastProcessor(long lastProcessor) {
		this.lastProcessor = lastProcessor;
	}

	public long getPublisher() {
		return publisher;
	}

	public void setPublisher(long publisher) {
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
