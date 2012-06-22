package de.lemo.dms.connectors.clix2010.clixDBClass;

public class ForumEntryState {
	
	private ForumEntryStatePK id;
	
	private long user;
	private long forum;
	private String lastUpdated;
	private long entry;
	

	public ForumEntryStatePK getId() {
		return id;
	}


	public void setId(ForumEntryStatePK id) {
		this.id = id;
	}


	public long getUser() {
		return user;
	}


	public void setUser(long user) {
		this.user = user;
	}


	public long getForum() {
		return forum;
	}


	public void setForum(long forum) {
		this.forum = forum;
	}


	public String getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	public long getEntry() {
		return entry;
	}


	public void setEntry(long entry) {
		this.entry = entry;
	}


	public ForumEntryState()
	{}

}
