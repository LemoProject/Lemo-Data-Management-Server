/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_submissions_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class AssignmentSubmissionsLMS {

	private long id;
	private long assignment;
	private String userid;
	private long grade;
	private long timecreated;
	private long timemodified;
	private long teacher;
	private long timemarked;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getAssignment() {
		return this.assignment;
	}

	public void setAssignment(final long assignment) {
		this.assignment = assignment;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
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

	public long getTeacher() {
		return this.teacher;
	}

	public void setTeacher(final long teacher) {
		this.teacher = teacher;
	}

	public long getTimemarked() {
		return this.timemarked;
	}

	public void setTimemarked(final long timemarked) {
		this.timemarked = timemarked;
	}

	public void setGrade(final long grade) {
		this.grade = grade;
	}

	public long getGrade() {
		return this.grade;
	}
}
