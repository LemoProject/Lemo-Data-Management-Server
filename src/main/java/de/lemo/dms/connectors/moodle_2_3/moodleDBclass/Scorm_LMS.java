package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Scorm_LMS {
	
	private long id;
	private String name;
	private long course;
	private double maxgrade;
	private long timemodified;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCourse() {
		return course;
	}
	public void setCourse(long course) {
		this.course = course;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public double getMaxgrade() {
		return maxgrade;
	}
	public void setMaxgrade(double maxgrade) {
		this.maxgrade = maxgrade;
	}

}
