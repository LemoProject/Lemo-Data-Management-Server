/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/ChatLog_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class ChatLog_LMS {

	private long id;
	private long chat_Id;
	private long user_Id;
	private String message;
	private long timestamp;

	public long getUser_Id() {
		return this.user_Id;
	}

	public void setUser_Id(final long userid) {
		this.user_Id = userid;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getChat_Id() {
		return this.chat_Id;
	}

	public void setChat_Id(final long chat_id) {
		this.chat_Id = chat_id;
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
