/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assign_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table Assign.
 * 
 * @author S.Schwarzrock
 *
 */
public class AssignLMS {

	private long id;
	private long course;
	private String name;
	private String description;
	private long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timeModified) {
		this.timemodified = timeModified;
	}

}
