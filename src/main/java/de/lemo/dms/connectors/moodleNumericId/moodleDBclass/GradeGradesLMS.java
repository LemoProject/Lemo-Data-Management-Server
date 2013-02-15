/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Grade_grades_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table GradeGrades.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class GradeGradesLMS {

	private long id;
	private long userid;
	private long itemid;
	private Double rawgrade;
	private Double finalgrade;
	private Long timecreated;
	private Long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getItemid() {
		return this.itemid;
	}

	public void setItemid(final long itemid) {
		this.itemid = itemid;
	}

	public Double getRawgrade() {
		return this.rawgrade;
	}

	public void setRawgrade(final Double rawgrade) {
		this.rawgrade = rawgrade;
	}

	public Double getFinalgrade() {
		return this.finalgrade;
	}

	public void setFinalgrade(final Double finalgrade) {
		this.finalgrade = finalgrade;
	}

	public Long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final Long timecreated) {
		this.timecreated = timecreated;
	}

	public Long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final Long timemodified) {
		this.timemodified = timemodified;
	}
}
