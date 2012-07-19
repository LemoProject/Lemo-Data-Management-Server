package de.lemo.dms.connectors.clix2010.clixDBClass;

public class Chatroom {

	private long id;
	private String title;
	private String lastUpdated;
	
	public Chatroom()
	{
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
}
