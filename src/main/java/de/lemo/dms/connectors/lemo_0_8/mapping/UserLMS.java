/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/UserLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/UserLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the table resource. */
@Entity
@Table(name = "user")
public class UserLMS{


	private long id;	
	private String login;
	private long gender;
	private long lastLogin;
	private long firstAccess;
	private long lastAccess;
	private long currentLogin;
	private Long platform;


	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the user
	 */
	@Id
	public long getId() {
		return this.id;
	}


	@Column(name="login")
	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}


	@Column(name="gender")
	public long getGender() {
		return this.gender;
	}

	public void setGender(final long gender) {
		this.gender = gender;
	}

	public void setId(final long id)
	{
		this.id = id;
	}

	/**
	 * standard getter for the attribute lastlogin
	 * 
	 * @return the timestamp of the last time the user has logged in
	 */
	@Column(name="lastlogin")
	public long getLastLogin() {
		return this.lastLogin;
	}

	/**
	 * standard setter for the attribute lastlogin
	 * 
	 * @param lastLogin
	 *            the timestamp of the last time the user has logged in
	 */
	public void setLastLogin(final long lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * standard getter for the attribute firstaccess
	 * 
	 * @return the timestamp when the user has made his first access to the LMS
	 */
	@Column(name="firstaccess")
	public long getFirstAccess() {
		return this.firstAccess;
	}

	/**
	 * standard setter for the attribute firstaccess
	 * 
	 * @param firstAccess
	 *            the timestamp when the user has made his first access to the LMS
	 */
	public void setFirstAccess(final long firstAccess) {
		this.firstAccess = firstAccess;
	}

	/**
	 * standard getter for the attribute lastaccess
	 * 
	 * @return the timestamp when the user has made the last access to the LMS
	 */
	@Column(name="lastaccess")
	public long getLastAccess() {
		return this.lastAccess;
	}

	/**
	 * standard setter for the attribute lastaccess
	 * 
	 * @param lastAccess
	 *            the timestamp when the user has made the last access to the LMS
	 */
	public void setLastAccess(final long lastAccess) {
		this.lastAccess = lastAccess;
	}

	/**
	 * standard getter for the attribute currentlogin
	 * 
	 * @return the timestamp when the user has logged into the current session
	 */
	@Column(name="currentlogin")
	public long getCurrentLogin() {
		return this.currentLogin;
	}

	/**
	 * standard setter for the attribute currentlogin
	 * 
	 * @param currentLogin
	 *            the timestamp when the user has logged into the current session
	 */
	public void setCurrentLogin(final long currentLogin) {
		this.currentLogin = currentLogin;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
