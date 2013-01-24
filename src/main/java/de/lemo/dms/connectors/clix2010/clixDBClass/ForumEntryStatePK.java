/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryStatePK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class ForumEntryStatePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7895343428630427499L;
	private Long user;
	private Long entry;

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof ForumEntryStatePK)) {
			return false;
		}
		final ForumEntryStatePK a = (ForumEntryStatePK) arg;
		if (a.getUser() != this.user) {
			return false;
		}
		if (a.getEntry() != this.entry) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hc;
		hc = this.entry.hashCode();
		hc = (17 * hc) + this.user.hashCode();
		return hc;
	}

	public long getUser() {
		return this.user;
	}

	public void setUser(final long user) {
		this.user = user;
	}

	public long getEntry() {
		return this.entry;
	}

	public void setEntry(final long entry) {
		this.entry = entry;
	}

	public ForumEntryStatePK()
	{
	}

}
