package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the relationship between groups and user.*/
public class GroupUserMining implements IMappingClass {

	private long id;
	private GroupMining group;
	private	UserMining user;
	private long timestamp;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof GroupUserMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof GroupUserMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between groups and users
	 */		
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between groups and users
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut timestamp
	 * @return the timestamp when the user enters the group
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribut timestamp
	 * @param timestamp the timestamp when the user enters the group
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/** standard getter for the attribut user
	 * @return the user who is member of the group
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who is member of the group
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribut user
	 * @param user the id of the user who is member of the group
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addGroup_user(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addGroup_user(this);
		}
	}
	/** standard getter for the attribut group
	 * @return the group in which the user is member
	 */	
	public GroupMining getGroup() {
		return group;
	}
	/** standard setter for the attribut group
	 * @param group the group in which the user is member
	 */	
	public void setGroup(GroupMining group) {
		this.group = group;
	}
	/** parameterized setter for the attribut group
	 * @param group the group in which the user is member
	 * @param groupMining a list of new added groups, which is searched for the group with the id submitted in the group parameter
	 * @param oldGroupMining a list of groups in the miningdatabase, which is searched for the group with the id submitted in the group parameter
	 */	
	public void setGroup(long group, HashMap<Long, GroupMining> groupMining, HashMap<Long, GroupMining> oldGroupMining) {	
		
		if(groupMining.get(group) != null)
		{
			this.group = groupMining.get(group);
			groupMining.get(group).addGroup_user(this);
		}
		if(this.group == null && oldGroupMining.get(group) != null)
		{
			this.group = oldGroupMining.get(group);
			oldGroupMining.get(group).addGroup_user(this);
		}
	}
}