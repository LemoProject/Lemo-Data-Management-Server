/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Enrol_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table Enrol.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class EnrolLMS {

	private long id;
	private long courseid;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCourseid() {
		return this.courseid;
	}

	public void setCourseid(final long courseid) {
		this.courseid = courseid;
	}

}
