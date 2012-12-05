package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class ForumEntryStatePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7895343428630427499L;
	private Long user;
	private Long entry;
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof ForumEntryStatePK))
			return false;
		ForumEntryStatePK a = (ForumEntryStatePK)arg;
		if(a.getUser() != this.user)
			return false;
		if(a.getEntry() != this.entry)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int hc;
		hc = entry.hashCode();
		hc = 17 * hc + user.hashCode();
		return hc;
	}
	
	
	public long getUser() {
		return user;
	}


	public void setUser(long user) {
		this.user = user;
	}


	public long getEntry() {
		return entry;
	}


	public void setEntry(long entry) {
		this.entry = entry;
	}


	public ForumEntryStatePK()
	{}

}
