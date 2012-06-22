package de.lemo.dms.connectors.clix2010.clixDBClass;

public class ForumEntry {

	private long id;
	private long forum;
	private long lastUpdater;
	private String lastUpdated;
	private String title;
	private String content;
	
	public ForumEntry()
	{}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getForum() {
		return forum;
	}

	public void setForum(long forum) {
		this.forum = forum;
	}

	public long getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
