/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/PlatformMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/PlatformMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * Bean class for the platforms 
 *
 */
public class PlatformMining implements IMappingClass {

	private Long id;
	private String name;
	private String type;
	private Long prefix;

	public PlatformMining(final Long id, final String name, final String type, final Long prefix)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.prefix = prefix;
	}

	public PlatformMining()
	{

	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public boolean equals(final IMappingClass o) {
		if (!(o instanceof PlatformMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof PlatformMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.intValue();
	}

	public Long getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final Long prefix) {
		this.prefix = prefix;
	}

}
