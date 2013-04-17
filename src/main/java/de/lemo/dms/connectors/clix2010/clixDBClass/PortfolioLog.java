/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PortfolioLog.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table PortfolioLog.
 * 
 * @author S.Schwarzrock
 *
 */
public class PortfolioLog implements IClixMappingClass {

	private Long id;
	private Long component;
	private Long person;
	private Long typeOfModification;
	private String lastUpdated;
	private Long lastUpdater;

	public PortfolioLog()
	{

	}

	public String getString()
	{
		return "PortfolioLog$$$"
				+ this.id + "$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getComponent() + "$$$"
				+ this.getLastUpdater() + "$$$"
				+ this.getPerson() + "$$$"
				+ this.getTypeOfModification();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public Long getTypeOfModification() {
		return this.typeOfModification;
	}

	public void setTypeOfModification(final Long typeOfModification) {
		this.typeOfModification = typeOfModification;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getLastUpdater() {
		return this.lastUpdater;
	}

	public void setLastUpdater(final Long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

}
