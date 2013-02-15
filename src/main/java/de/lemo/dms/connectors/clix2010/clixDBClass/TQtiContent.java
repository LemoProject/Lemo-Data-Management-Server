/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContent.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiContent.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiContent implements IClixMappingClass {

	private Long id;
	private String created;
	private String lastUpdated;
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getString()
	{
		return "TQtiContent$$$"
				+ this.id + "$$$"
				+ this.getCreated() + "$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getName();
	}

	public TQtiContent()
	{

	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(final String created) {
		this.created = created;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
