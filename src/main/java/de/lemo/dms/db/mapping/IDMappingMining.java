/**
 * File ./src/main/java/de/lemo/dms/db/mapping/IDMappingMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/IDMappingMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/**
 * Bean for the id mappig with hibernate
 *
 */
@Entity
@Table(name = "id_mapping")
public class IDMappingMining implements IMappingClass {

	private Long id;
	private String hash;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof IDMappingMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof IDMappingMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.intValue();
	}
	
	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	public IDMappingMining()
	{

	}

	public IDMappingMining(final long id, final String hash)
	{
		this.id = id;
		this.hash = hash;
		this.platform = 0L;
	}

	public IDMappingMining(final long id, final String hash, final Long platform)
	{
		this.id = id;
		this.hash = hash;
		this.platform = platform;
	}
	
	@Override
	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="hash")
	public String getHash() {
		return this.hash;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

}
