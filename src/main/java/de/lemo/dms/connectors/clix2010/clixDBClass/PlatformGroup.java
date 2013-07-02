/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroup.java
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
