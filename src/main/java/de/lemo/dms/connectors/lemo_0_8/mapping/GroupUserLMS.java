/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/GroupUserLMS.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/GroupUserLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * This class represents the relationship between groups and user.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "group_user")
public class GroupUserLMS{

	private long id;
	private long group;
	private long user;
	private long timestamp;
	private Long platform;

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between groups and users
	 */
	@Id
	public long getId() {
		return this.id;
	}
	
	
	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between groups and users
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp when the user enters the group
	 */
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp when the user enters the group
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who is member of the group
	 */
	@Column(name="user_id")
	public long getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is member of the group
	 */
	public void setUser(final long user) {
		this.user = user;
	}

	/**
	 * standard getter for the attribute group
	 * 
	 * @return the group in which the user is member
	 */
	@Column(name="group_id")
	public long getGroup() {
		return this.group;
	}

	/**
	 * standard setter for the attribute group
	 * 
	 * @param group
	 *            the group in which the user is member
	 */
	public void setGroup(final long group) {
		this.group = group;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}