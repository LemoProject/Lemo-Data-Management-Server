/**
 * File ./main/java/de/lemo/dms/connectors/moodle/moodleDBclass/ChatLMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle.moodleDBclass;

public class ChatLMS {

	private long id;
	private String title;
	private String description;
	private long chattime;
	private long timemodified;
	private long course;

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

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public long getChattime() {
		return this.chattime;
	}

	public void setChattime(final long chattime) {
		this.chattime = chattime;
	}
}
