/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/Chatroom.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table Chatroom.
 * 
 * @author S.Schwarzrock
 *
 */
public class Chatroom implements IClixMappingClass {

	private long id;
	private String title;
	private String lastUpdated;

	public Chatroom()
	{

	}

	public String getString()
	{
		return "Chatroom$$$"
				+ this.id + "$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getTitle();
	}

	public long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
