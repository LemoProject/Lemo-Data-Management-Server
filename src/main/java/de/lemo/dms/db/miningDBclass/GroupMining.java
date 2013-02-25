/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/GroupMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * This class represents the table group.
 * @author Sebastian Schwarzrock
 */
public class GroupMining implements IMappingClass {

	private long id;
	private long timeCreated;
	private long timeModified;
	private Long platform;

	private Set<CourseGroupMining> courseGroups = new HashSet<CourseGroupMining>();
	private Set<GroupUserMining> groupUsers = new HashSet<GroupUserMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof GroupMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof GroupMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the group
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the group
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the group was created
	 */
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the group was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the group was changed the last time
	 */
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the group was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute course_groups
	 * 
	 * @param courseGroup
	 *            a set of entries in the course_group table which relate the group to the courses
	 */
	public void setCourseGroups(final Set<CourseGroupMining> courseGroup) {
		this.courseGroups = courseGroup;
	}

	/**
	 * standard getter for the attribute course_groups
	 * 
	 * @return a set of entries in the course_group table which relate the group to the courses
	 */
	public Set<CourseGroupMining> getCourseGroups() {
		return this.courseGroups;
	}

	/**
	 * standard add method for the attribute course_group
	 * 
	 * @param courseGroup
	 *            this entry will be added to the list of course_group in this group
	 */
	public void addCourseGroup(final CourseGroupMining courseGroup) {
		this.courseGroups.add(courseGroup);
	}

	/**
	 * standard setter for the attribute group_enrol
	 * 
	 * @param groupUsers
	 *            a set of entries in the group_user table which relate the group to the users
	 */
	public void setGroupUsers(final Set<GroupUserMining> groupUsers) {
		this.groupUsers = groupUsers;
	}

	/**
	 * standard getter for the attribute group_user
	 * 
	 * @return a set of entries in the group_user table which relate the group to the users
	 */
	public Set<GroupUserMining> getGroupUsers() {
		return this.groupUsers;
	}

	/**
	 * standard add method for the attribute group_user
	 * 
	 * @param groupUser
	 *            this entry will be added to the list of group_user in this group
	 */
	public void addGroupUser(final GroupUserMining groupUser) {
		this.groupUsers.add(groupUser);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
