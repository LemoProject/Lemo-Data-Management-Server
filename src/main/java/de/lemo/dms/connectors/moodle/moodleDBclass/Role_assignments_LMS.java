package de.lemo.dms.connectors.moodle.moodleDBclass;

public class Role_assignments_LMS {
	
	private long id;
	private long roleid;
	private long contextid;
	private String userid;
	private long timestart;
	private long timeend;
	private long timemodified;
	private long modifierid;	
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	public long getRoleid() {
		return roleid;
	}
	public long getContextid() {
		return contextid;
	}
	public void setContextid(long contextid) {
		this.contextid = contextid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getTimestart() {
		return timestart;
	}
	public void setTimestart(long timestart) {
		this.timestart = timestart;
	}
	public long getTimeend() {
		return timeend;
	}
	public void setTimeend(long timeend) {
		this.timeend = timeend;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public void setModifierid(long modifierid) {
		this.modifierid = modifierid;
	}
	public long getModifierid() {
		return modifierid;
	}	
}
