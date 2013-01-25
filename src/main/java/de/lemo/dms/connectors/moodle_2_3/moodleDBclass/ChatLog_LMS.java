/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ChatLog_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class ChatLog_LMS {

	private long id;
	private long chat_id;
	private String userid;
	private String message;
	private long timestamp;

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getChat_id() {
		return this.chat_id;
	}

	public void setChat_id(final long chat_id) {
		this.chat_id = chat_id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

}
