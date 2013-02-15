/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/GroupsMembersLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

/**
 * Mapping class for table GroupsMembers.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class GroupsMembersLMS {

	private long id;
	private long groupid;
	private String userid;
	private long timeadded;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getGroupid() {
		return this.groupid;
	}

	public void setGroupid(final long groupid) {
		this.groupid = groupid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public long getTimeadded() {
		return this.timeadded;
	}

	public void setTimeadded(final long timeadded) {
		this.timeadded = timeadded;
	}
}
