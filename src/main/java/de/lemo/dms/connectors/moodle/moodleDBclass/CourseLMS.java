package de.lemo.dms.connectors.moodle.moodleDBclass;

public class CourseLMS {
	
	private long id;
	private String fullname;
	private String summary;
	private String shortname;
	private long startdate;
	private long timecreated;
	private long timemodified;
	private long enrolstartdate;
	private long enrolenddate;
	private long enrolperiod;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public long getEnrolstartdate() {
		return enrolstartdate;
	}
	public void setEnrolstartdate(long enrolstartdate) {
		this.enrolstartdate = enrolstartdate;
	}
	public long getEnrolenddate() {
		return enrolenddate;
	}
	public void setEnrolenddate(long enrolenddate) {
		this.enrolenddate = enrolenddate;
	}
	public long getStartdate() {
		return startdate;
	}
	public void setStartdate(long startdate) {
		this.startdate = startdate;
	}
	public long getEnrolperiod() {
		return enrolperiod;
	}
	public void setEnrolperiod(long enrolperiod) {
		this.enrolperiod = enrolperiod;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getShortname() {
		return shortname;
	}
}
