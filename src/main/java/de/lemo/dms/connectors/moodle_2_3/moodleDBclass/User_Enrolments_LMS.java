package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class User_Enrolments_LMS {

	private long id;
	private long enrolid;
	private long timestart;
	private long timeend;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEnrolid() {
		return enrolid;
	}
	public void setEnrolid(long enrolid) {
		this.enrolid = enrolid;
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
	
	
	
}
