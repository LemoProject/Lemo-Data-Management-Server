package de.lemo.dms.connectors.moodle.moodleDBclass;

public class ForumDiscussionsLMS {

	private long id;
	private long forum;
	private String userid;
	private String name;
	private long firstpost;
	private long timemodified;
	private String usermodified;
	private long timestart;
	private long timeend;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getForum() {
		return forum;
	}
	public void setForum(long forum) {
		this.forum = forum;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getFirstpost() {
		return firstpost;
	}
	public void setFirstpost(long firstpost) {
		this.firstpost = firstpost;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public String getUsermodified() {
		return usermodified;
	}
	public void setUsermodified(String usermodified) {
		this.usermodified = usermodified;
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
