/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Role_assignments_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table RoleAssignments.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class RoleAssignmentsLMS {

	private long id;
	private long roleid;
	private long contextid;
	private long userid;
	private long timestart;
	private long timeend;
	private long timemodified;
	private long modifierid;

	public void setId(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setRoleid(final long roleid) {
		this.roleid = roleid;
	}

	public long getRoleid() {
		return this.roleid;
	}

	public long getContextid() {
		return this.contextid;
	}

	public void setContextid(final long contextid) {
		this.contextid = contextid;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getTimestart() {
		return this.timestart;
	}

	public void setTimestart(final long timestart) {
		this.timestart = timestart;
	}

	public long getTimeend() {
		return this.timeend;
	}

	public void setTimeend(final long timeend) {
		this.timeend = timeend;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public void setModifierid(final long modifierid) {
		this.modifierid = modifierid;
	}

	public long getModifierid() {
		return this.modifierid;
	}
}
