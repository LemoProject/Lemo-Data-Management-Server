/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryState.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class ForumEntryState implements IClixMappingClass {

	private ForumEntryStatePK id;

	private Long user;
	private Long forum;
	private String lastUpdated;
	private Long entry;

	public ForumEntryStatePK getId() {
		return this.id;
	}

	public String getString()
	{
		return "ForumEntryState$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getEntry() + "$$$"
				+ this.getForum() + "$$$"
				+ this.getUser();
	}

	public void setId(final ForumEntryStatePK id) {
		this.id = id;
	}

	public Long getUser() {
		return this.user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	public Long getForum() {
		return this.forum;
	}

	public void setForum(final Long forum) {
		this.forum = forum;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getEntry() {
		return this.entry;
	}

	public void setEntry(final Long entry) {
		this.entry = entry;
	}

	public ForumEntryState()
	{
	}

}
