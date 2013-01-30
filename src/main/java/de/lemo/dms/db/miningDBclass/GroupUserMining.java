/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/GroupUserMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the relationship between groups and user.
 * @author Sebastian Schwarzrock
 */
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

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}