/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/RoleLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/RoleLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the table quiz. */
@Entity
@Table(name = "role")
public class RoleLMS{

	private long id;
	private String name;
	private String shortname;
	// text
	private String description;
	private long sortOrder;
	private Long platform;
	private long type;


	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the role
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the role
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute name
	 * 
	 * @param name
	 *            the full name of the role
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * standard getter for the attribute name
	 * 
	 * @return the full name of the role
	 */
	@Column(name="name")
	public String getName() {
		return this.name;
	}

	/**
	 * standard setter for the attribute shortname
	 * 
	 * @param shortname
	 *            the shortname of the role
	 */
	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	/**
	 * standard getter for the attribute shortname
	 * 
	 * @return the shortname of the role
	 */
	@Column(name="shortname")
	public String getShortname() {
		return this.shortname;
	}

	/**
	 * standard setter for the attribute description
	 * 
	 * @param description
	 *            a description of the role
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * standard getter for the attribute description
	 * 
	 * @return a description of the role
	 */
	@Column(name="description")
	public String getDescription() {
		return this.description;
	}

	/**
	 * standard setter for the attribute sortorder
	 * 
	 * @param sortorder
	 *            the sortorder for the roles
	 */
	public void setSortOrder(final long sortorder) {
		this.sortOrder = sortorder;
	}

	/**
	 * standard getter for the attribute sortorder
	 * 
	 * @return the sortorder for the roles
	 */
	@Column(name="sortorder")
	public long getSortOrder() {
		return this.sortOrder;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Column(name="type")
	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}
}
