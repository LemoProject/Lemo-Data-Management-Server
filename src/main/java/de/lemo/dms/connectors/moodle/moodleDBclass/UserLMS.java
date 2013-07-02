/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle/moodleDBclass/UserLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/UserLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table User.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class UserLMS {

	private String id;
	private long firstaccess;
	private long lastaccess;
	private long lastlogin;
	private long currentlogin;
	private long timemodified;
	private String login;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public long getFirstaccess() {
		return this.firstaccess;
	}

	public void setFirstaccess(final long firstaccess) {
		this.firstaccess = firstaccess;
	}

	public long getLastaccess() {
		return this.lastaccess;
	}

	public void setLastaccess(final long lastaccess) {
		this.lastaccess = lastaccess;
	}

	public long getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(final long lastlogin) {
		this.lastlogin = lastlogin;
	}

	public long getCurrentlogin() {
		return this.currentlogin;
	}

	public void setCurrentlogin(final long currentlogin) {
		this.currentlogin = currentlogin;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}
}
