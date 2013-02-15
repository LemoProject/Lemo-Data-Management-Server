/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TGroupFullSpecification.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TGroupFullSpecification.
 * 
 * @author S.Schwarzrock
 *
 */
public class TGroupFullSpecification implements IClixMappingClass {

	private Long group;
	private Long person;

	public Long getGroup() {
		return this.group;
	}

	public String getString()
	{
		return "TGroupFullSpecification$$$"
				+ this.getGroup() + "$$$"
				+ this.getPerson();
	}

	public void setGroup(final Long group) {
		this.group = group;
	}

	public Long getPerson() {
		return this.person;
	}

	public void setPerson(final Long person) {
		this.person = person;
	}

	public TGroupFullSpecification()
	{

	}

}
