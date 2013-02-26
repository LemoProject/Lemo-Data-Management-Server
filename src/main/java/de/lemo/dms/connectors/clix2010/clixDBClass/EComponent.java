/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComponent.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComponent.
 * 
 * @author S.Schwarzrock
 *
 */
public class EComponent implements IClixMappingClass {

	private Long id;
	private Long type;
	private String name;
	private String creationDate;
	private String lastUpdated;
	private String startDate;
	private String description;
	private final static String DOLLAR = "$$$";

	public EComponent()
	{

	}

	public String getString()
	{
		return "EComponent" + DOLLAR
				+ this.id + DOLLAR
				+ this.getCreationDate() + DOLLAR
				+ this.getDescription() + DOLLAR
				+ this.getLastUpdated() + DOLLAR
				+ this.getName() + DOLLAR
				+ this.getStartDate() + DOLLAR
				+ this.getType();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(final Long type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
