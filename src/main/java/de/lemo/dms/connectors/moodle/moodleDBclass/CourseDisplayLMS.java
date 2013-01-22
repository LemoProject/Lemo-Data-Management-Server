package de.lemo.dms.connectors.moodle.moodleDBclass;

public class CourseDisplayLMS {

	private long id;
	private long course;
	private String userid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCourse() {
		return course;
	}
	public void setCourse(long course) {
		this.course = course;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
