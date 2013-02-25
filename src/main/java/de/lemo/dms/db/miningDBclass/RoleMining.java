/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/RoleMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table quiz. */
public class RoleMining implements IMappingClass {

	private long id;
	private String name;
	private String shortname;
	// text
	private String description;
	private long sortOrder;
	private Long platform;
	private long type;

	private Set<CourseUserMining> courseUsers = new HashSet<CourseUserMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof RoleMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof RoleMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the role
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the role
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute name
	 * 
	 * @param name
	 *            the full name of the role
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * standard getter for the attribute name
	 * 
	 * @return the full name of the role
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * standard setter for the attribute shortname
	 * 
	 * @param shortname
	 *            the shortname of the role
	 */
	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	/**
	 * standard getter for the attribute shortname
	 * 
	 * @return the shortname of the role
	 */
	public String getShortname() {
		return this.shortname;
	}

	/**
	 * standard setter for the attribute description
	 * 
	 * @param description
	 *            a description of the role
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * standard getter for the attribute description
	 * 
	 * @return a description of the role
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * standard setter for the attribute sortorder
	 * 
	 * @param sortorder
	 *            the sortorder for the roles
	 */
	public void setSortOrder(final long sortorder) {
		this.sortOrder = sortorder;
	}

	/**
	 * standard getter for the attribute sortorder
	 * 
	 * @return the sortorder for the roles
	 */
	public long getSortOrder() {
		return this.sortOrder;
	}

	/**
	 * standard setter for the attribute course_user
	 * 
	 * @param courseUsers
	 *            a set of entries in the course_user table which relate this role with users
	 */
	public void setCourseUsers(final Set<CourseUserMining> courseUsers) {
		this.courseUsers = courseUsers;
	}

	/**
	 * standard getter for the attribute course_user
	 * 
	 * @return a set of entries in the course_user table which relate this role with users
	 */
	public Set<CourseUserMining> getCourseUsers() {
		return this.courseUsers;
	}

	/**
	 * standard add method for the attribute course_user
	 * 
	 * @param courseUser
	 *            this entry will be added to the list of course_user in this role
	 */
	public void addCourseUser(final CourseUserMining courseUser) {
		this.courseUsers.add(courseUser);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}
}
