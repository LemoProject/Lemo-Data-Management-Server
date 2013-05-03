/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecification.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table PLatformGroupSpecification.
 * 
 * @author S.Schwarzrock
 *
 */
public class PlatformGroupSpecification implements IClixMappingClass {

	private Long group;
	private Long person;
	private PlatformGroupSpecificationPK id;

	public PlatformGroupSpecificationPK getId() {
		return this.id;
	}

	public String getString()
	{
		return "PlatformGroupSpecification$$$"
				+ this.getGroup() + "$$$" +
				this.getPerson();
	}

	public void setId(final PlatformGroupSpecificationPK id) {
		this.id = id;
	}

	public Long getGroup() {
		return this.group;
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

	public PlatformGroupSpecification()
	{

	}

}
