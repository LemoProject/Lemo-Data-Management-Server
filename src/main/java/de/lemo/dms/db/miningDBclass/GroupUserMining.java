/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/GroupUserMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/GroupUserMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the relationship between groups and user.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "group_user")
public class GroupUserMining implements IMappingClass {

	private long id;
	private GroupMining group;
	private UserMining user;
	private long timestamp;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof GroupUserMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof GroupUserMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between groups and users
	 */
	@Override
	@Id
	public long getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who is member of the group
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute user
	 * 
	 * @param user
	 *            the id of the user who is member of the group
	 * @param userMining
	 *            a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining
	 *            a list of user in the miningdatabase, which is searched for the user with the id submitted in the user
	 *            parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addGroupUser(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addGroupUser(this);
		}
	}

	/**
	 * standard getter for the attribute group
	 * 
	 * @return the group in which the user is member
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_id")
	public GroupMining getGroup() {
		return this.group;
	}

	/**
	 * standard setter for the attribute group
	 * 
	 * @param group
	 *            the group in which the user is member
	 */
	public void setGroup(final GroupMining group) {
		this.group = group;
	}

	/**
	 * parameterized setter for the attribute group
	 * 
	 * @param group
	 *            the group in which the user is member
	 * @param groupMining
	 *            a list of new added groups, which is searched for the group with the id submitted in the group
	 *            parameter
	 * @param oldGroupMining
	 *            a list of groups in the miningdatabase, which is searched for the group with the id submitted in the
	 *            group parameter
	 */
	public void setGroup(final long group, final Map<Long, GroupMining> groupMining,
			final Map<Long, GroupMining> oldGroupMining) {

		if (groupMining.get(group) != null)
		{
			this.group = groupMining.get(group);
			groupMining.get(group).addGroupUser(this);
		}
		if ((this.group == null) && (oldGroupMining.get(group) != null))
		{
			this.group = oldGroupMining.get(group);
			oldGroupMining.get(group).addGroupUser(this);
		}
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}