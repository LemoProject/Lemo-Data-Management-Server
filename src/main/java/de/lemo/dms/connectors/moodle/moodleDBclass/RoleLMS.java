/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle/moodleDBclass/RoleLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/RoleLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table Role.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class RoleLMS {

	private long id;
	private String name;
	private String shortname;
	private String description;
	private long sortorder;

	public void setId(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public long getSortorder() {
		return this.sortorder;
	}

	public void setSortorder(final long sortorder) {
		this.sortorder = sortorder;
	}

}
