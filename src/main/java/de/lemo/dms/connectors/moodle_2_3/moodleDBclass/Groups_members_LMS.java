package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Groups_members_LMS {

	private long id;
	private long groupid;
	private String userid;
	private long timeadded;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getGroupid() {
		return groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getTimeadded() {
		return timeadded;
	}
	public void setTimeadded(long timeadded) {
		this.timeadded = timeadded;
	}
}
