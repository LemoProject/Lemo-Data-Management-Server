/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/PlatformMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * Bean class for the platforms 
 *
 */
public class PlatformMining implements IMappingClass {

	private Long id;
	private String name;
	private String type;
	private Long prefix;

	public PlatformMining(final Long id, final String name, final String type, final Long prefix)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.prefix = prefix;
	}

	public PlatformMining()
	{

	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public boolean equals(final IMappingClass o) {
		if (!(o instanceof PlatformMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof PlatformMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.intValue();
	}

	public Long getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final Long prefix) {
		this.prefix = prefix;
	}

}
