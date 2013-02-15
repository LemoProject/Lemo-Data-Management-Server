/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ScormSessionTimes.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table ScormSessionTimes.
 * 
 * @author S.Schwarzrock
 *
 */
public class ScormSessionTimes implements IClixMappingClass {

	private ScormSessionTimesPK id;

	private Long component;
	private Long person;
	private String score;
	private String lastUpdated;
	private String status;

	public ScormSessionTimesPK getId() {
		return this.id;
	}

	public String getString()
	{
		return "ScormSessionTimes$$$"
				+ this.getLastUpdated() + "$$$"
				+ this.getScore() + "$$$"
				+ this.getStatus() + "$$$"
				+ this.getComponent() + "$$$"
				+ this.getPerson();
	}

	public void setId(final ScormSessionTimesPK id) {
		this.id = id;
	}

	public ScormSessionTimes()
	{

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

	public String getScore() {
		return this.score;
	}

	public void setScore(final String score) {
		this.score = score;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
