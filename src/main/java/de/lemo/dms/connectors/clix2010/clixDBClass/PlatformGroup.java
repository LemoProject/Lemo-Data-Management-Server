/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroup.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table PlatformGroup.
 * 
 * @author S.Schwarzrock
 *
 */
public class PlatformGroup implements IClixMappingClass {

	private Long id;
	private Long typeId;
	private String lastUpdated;
	private String created;

	public Long getId() {
		return this.id;
	}

	public String getString()
	{
		return "PlatformGroup$$$"
				+ this.id + "$$$"
				+ this.getCreated() + "$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getTypeId();
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(final Long typeId) {
		this.typeId = typeId;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(final String created) {
		this.created = created;
	}

	public PlatformGroup()
	{

	}

}
