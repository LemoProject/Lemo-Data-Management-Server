/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Groups_members_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table GroupsMembers.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class GroupsMembersLMS {

	private long id;
	private long groupid;
	private long userid;
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

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getTimeadded() {
		return this.timeadded;
	}

	public void setTimeadded(final long timeadded) {
		this.timeadded = timeadded;
	}
}
