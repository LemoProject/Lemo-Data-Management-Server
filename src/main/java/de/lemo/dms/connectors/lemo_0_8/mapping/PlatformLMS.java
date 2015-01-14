/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/PlatformLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/PlatformLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean class for the platforms 
 *
 */
@Entity
@Table(name = "platform")
public class PlatformLMS{

	private Long id;
	private String name;
	private String type;
	private Long prefix;

	@Column(name="name")
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name="type")
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Id
	public long getId() {
		return this.id;
	}

	@Column(name="prefix")
	public Long getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final Long prefix) {
		this.prefix = prefix;
	}

}
