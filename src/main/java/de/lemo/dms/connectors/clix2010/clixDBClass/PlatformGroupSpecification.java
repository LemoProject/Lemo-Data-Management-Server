/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecification.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecification.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table PLatformGroupSpecification.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "PLATFORMGROUPSPECIFICATION")
public class PlatformGroupSpecification implements IClixMappingClass {

	private Long group;
	private Long person;
	private PlatformGroupSpecificationPK id;

	@EmbeddedId
	public PlatformGroupSpecificationPK getId() {
		return this.id;
	}

	public void setId(final PlatformGroupSpecificationPK id) {
		this.id = id;
	}

	@Column(name="GROUP_ID")
	public Long getGroup() {
		return this.group;
	}

	public void setGroup(final Long group) {
		this.group = group;
	}

	@Column(name="PERSON_ID")
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
