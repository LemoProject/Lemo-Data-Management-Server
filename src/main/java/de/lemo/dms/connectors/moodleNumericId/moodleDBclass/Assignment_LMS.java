/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class Assignment_LMS {

	private long id;
	private long course;
	private String name;
	private String assignmenttype;
	private long timemodified;
	private long timeavailable;
	private long timedue;
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

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

	public String getAssignmenttype() {
		return this.assignmenttype;
	}

	public void setAssignmenttype(final String assignmenttype) {
		this.assignmenttype = assignmenttype;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public long getTimeavailable() {
		return this.timeavailable;
	}

	public void setTimeavailable(final long timeavailable) {
		this.timeavailable = timeavailable;
	}

	public long getTimedue() {
		return this.timedue;
	}

	public void setTimedue(final long timedue) {
		this.timedue = timedue;
	}
}
