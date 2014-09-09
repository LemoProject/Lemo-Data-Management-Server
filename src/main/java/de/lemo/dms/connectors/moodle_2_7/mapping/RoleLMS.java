/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/RoleLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Role_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Role.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_role")
public class RoleLMS {

	private long id;
	private String name;
	private String shortname;
	private String description;
	private long sortorder;
	private String archetype;

	public void setId(final long id) {
		this.id = id;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name="name")
	public String getName() {
		return this.name;
	}

	@Column(name="shortname")
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	@Column(name="description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(name="sortorder")
	public long getSortorder() {
		return this.sortorder;
	}

	public void setSortorder(final long sortorder) {
		this.sortorder = sortorder;
	}

	@Column(name="archetype")
	public String getArchetype() {
		return archetype;
	}

	public void setArchetype(String archetype) {
		this.archetype = archetype;
	}

}
