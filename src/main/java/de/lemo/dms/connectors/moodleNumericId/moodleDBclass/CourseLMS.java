/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Course_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table Course.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
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
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public long getEnrolstartdate() {
		return this.enrolstartdate;
	}

	public void setEnrolstartdate(final long enrolstartdate) {
		this.enrolstartdate = enrolstartdate;
	}

	public long getEnrolenddate() {
		return this.enrolenddate;
	}

	public void setEnrolenddate(final long enrolenddate) {
		this.enrolenddate = enrolenddate;
	}

	public long getStartdate() {
		return this.startdate;
	}

	public void setStartdate(final long startdate) {
		this.startdate = startdate;
	}

	public long getEnrolperiod() {
		return this.enrolperiod;
	}

	public void setEnrolperiod(final long enrolperiod) {
		this.enrolperiod = enrolperiod;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public String getShortname() {
		return this.shortname;
	}
}
