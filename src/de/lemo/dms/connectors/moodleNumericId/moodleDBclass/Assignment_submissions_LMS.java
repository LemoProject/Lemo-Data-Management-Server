package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class Assignment_submissions_LMS {

	private long id;
	private long assignment;
	private long userid;
	private long grade;
	private long timecreated;
	private long timemodified;
	private long teacher;
	private long timemarked;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAssignment() {
		return assignment;
	}
	public void setAssignment(long assignment) {
		this.assignment = assignment;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getTimecreated() {
		return timecreated;
	}
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public long getTeacher() {
		return teacher;
	}
	public void setTeacher(long teacher) {
		this.teacher = teacher;
	}
	public long getTimemarked() {
		return timemarked;
	}
	public void setTimemarked(long timemarked) {
		this.timemarked = timemarked;
	}
	public void setGrade(long grade) {
		this.grade = grade;
	}
	public long getGrade() {
		return grade;
	}
}
