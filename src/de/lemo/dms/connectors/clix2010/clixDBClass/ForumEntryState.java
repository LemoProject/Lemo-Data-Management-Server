package de.lemo.dms.connectors.clix2010.clixDBClass;

public class ForumEntryState {
	
	private ForumEntryStatePK id;
	
	private Long user;
	private Long forum;
	private String lastUpdated;
	private Long entry;
	

	public ForumEntryStatePK getId() {
		return id;
	}


	public void setId(ForumEntryStatePK id) {
		this.id = id;
	}


	public Long getUser() {
		return user;
	}


	public void setUser(Long user) {
		this.user = user;
	}


	public Long getForum() {
		return forum;
	}


	public void setForum(Long forum) {
		this.forum = forum;
	}


	public String getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	public Long getEntry() {
		return entry;
	}


	public void setEntry(Long entry) {
		this.entry = entry;
	}


	public ForumEntryState()
	{}

}
