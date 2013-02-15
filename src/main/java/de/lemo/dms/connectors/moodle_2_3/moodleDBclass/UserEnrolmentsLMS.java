/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/User_Enrolments_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table UserEnrolments.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class UserEnrolmentsLMS {

	private long id;
	private long enrolid;
	private long timestart;
	private long timeend;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getEnrolid() {
		return this.enrolid;
	}

	public void setEnrolid(final long enrolid) {
		this.enrolid = enrolid;
	}

	public long getTimestart() {
		return this.timestart;
	}

	public void setTimestart(final long timestart) {
		this.timestart = timestart;
	}

	public long getTimeend() {
		return this.timeend;
	}

	public void setTimeend(final long timeend) {
		this.timeend = timeend;
	}

}
