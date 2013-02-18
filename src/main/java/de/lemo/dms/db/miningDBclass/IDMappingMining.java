/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/IDMappingMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * Bean for the id mappig with hibernate
 *
 */
public class IDMappingMining implements IMappingClass {

	private Long id;
	private String hash;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof IDMappingMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof IDMappingMining)) {
			return true;
		}
		return false;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	public IDMappingMining()
	{

	}

	public IDMappingMining(final long id, final String hash)
	{
		this.id = id;
		this.hash = hash;
		this.platform = 0L;
	}

	public IDMappingMining(final long id, final String hash, final Long platform)
	{
		this.id = id;
		this.hash = hash;
		this.platform = platform;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

}
