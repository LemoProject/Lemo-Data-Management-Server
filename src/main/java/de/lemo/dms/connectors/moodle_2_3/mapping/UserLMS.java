/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/mapping/UserLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/mapping/User_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table User.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "mdl_user")
public class UserLMS {

	private long id;
	private long firstaccess;
	private long lastaccess;
	private long lastlogin;
	private long currentlogin;
	private long timemodified;
	private String username;

	@Column(name="username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="firstaccess")
	public long getFirstaccess() {
		return this.firstaccess;
	}

	public void setFirstaccess(final long firstaccess) {
		this.firstaccess = firstaccess;
	}

	@Column(name="lastaccess")
	public long getLastaccess() {
		return this.lastaccess;
	}

	public void setLastaccess(final long lastaccess) {
		this.lastaccess = lastaccess;
	}

	@Column(name="lastlogin")
	public long getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(final long lastlogin) {
		this.lastlogin = lastlogin;
	}

	@Column(name="currentlogin")
	public long getCurrentlogin() {
		return this.currentlogin;
	}

	public void setCurrentlogin(final long currentlogin) {
		this.currentlogin = currentlogin;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}
}
