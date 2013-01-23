package de.lemo.dms.connectors.moodle.moodleDBclass;

public class GradeItemsLMS {
	private long id;
	private long courseid;
	private String itemname;
	private String itemmodule;
	private Long iteminstance;
	private double grademax;
	private long timecreated;
	private long timemodified;
	

	public Long getIteminstance() {
		return iteminstance;
	}
	public void setIteminstance(Long iteminstance) {
		this.iteminstance = iteminstance;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItemmodule() {
		return itemmodule;
	}
	public void setItemmodule(String itemmodule) {
		this.itemmodule = itemmodule;
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
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public void setGrademax(double grademax) {
		this.grademax = grademax;
	}
	public double getGrademax() {
		return grademax;
	}

}
