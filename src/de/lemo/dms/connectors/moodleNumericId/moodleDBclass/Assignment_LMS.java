package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class Assignment_LMS {

	private long id;
	private long course;
	private String name;
	private String assignmenttype;
	private long timemodified;
	private long timeavailable;
	private long timedue;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignmenttype() {
		return assignmenttype;
	}
	public void setAssignmenttype(String assignmenttype) {
		this.assignmenttype = assignmenttype;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public long getTimeavailable() {
		return timeavailable;
	}
	public void setTimeavailable(long timeavailable) {
		this.timeavailable = timeavailable;
	}
	public long getTimedue() {
		return timedue;
	}
	public void setTimedue(long timedue) {
		this.timedue = timedue;
	}
}
