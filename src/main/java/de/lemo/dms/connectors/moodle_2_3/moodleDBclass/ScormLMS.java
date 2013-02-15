/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Scorm_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table Scorm.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ScormLMS {

	private long id;
	private String name;
	private long course;
	private double maxgrade;
	private long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public double getMaxgrade() {
		return this.maxgrade;
	}

	public void setMaxgrade(final double maxgrade) {
		this.maxgrade = maxgrade;
	}

}
