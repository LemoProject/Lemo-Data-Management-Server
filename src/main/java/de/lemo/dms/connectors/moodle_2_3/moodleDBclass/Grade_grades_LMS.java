package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Grade_grades_LMS {

	private long id;
	private String userid;
	private long itemid;
	private Double rawgrade;
	private Double finalgrade;
	private Long timecreated;
	private Long timemodified;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getItemid() {
		return itemid;
	}
	public void setItemid(long itemid) {
		this.itemid = itemid;
	}
	public Double getRawgrade() {
		return rawgrade;
	}
	public void setRawgrade(Double rawgrade) {
		this.rawgrade = rawgrade;
	}
	public Double getFinalgrade() {
		return finalgrade;
	}
	public void setFinalgrade(Double finalgrade) {
		this.finalgrade = finalgrade;
	}
	public Long getTimecreated() {
		return timecreated;
	}
	public void setTimecreated(Long timecreated) {
		this.timecreated = timecreated;
	}
	public Long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(Long timemodified) {
		this.timemodified = timemodified;
	}
}
