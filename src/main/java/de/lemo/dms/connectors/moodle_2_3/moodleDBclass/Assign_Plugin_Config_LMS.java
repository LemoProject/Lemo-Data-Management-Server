/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assign_Plugin_Config_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Assign_Plugin_Config_LMS {

	private long id;
	private long assignment;
	private String subtype;

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

	public String getSubtype() {
		return this.subtype;
	}

	public void setSubtype(final String subtype) {
		this.subtype = subtype;
	}

}
