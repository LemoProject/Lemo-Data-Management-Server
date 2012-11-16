package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Log_LMS {
	private long id;
	private long time;
	private long userid;
	private long course;
	private long cmid;
	private String module;
	private String action;
	private String info;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getCourse() {
		return course;
	}
	public void setCourse(long course) {
		this.course = course;
	}
	public void setCmid(long cmid) {
		this.cmid = cmid;
	}
	public long getCmid() {
		return cmid;
	}
}
