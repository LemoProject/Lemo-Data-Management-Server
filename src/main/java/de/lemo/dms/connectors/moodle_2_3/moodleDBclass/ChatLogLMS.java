/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ChatLog_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table ChatMessages.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ChatLogLMS {

	private long id;
	private long chat;
	private String user;
	private String message;
	private long timestamp;

	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getChat() {
		return this.chat;
	}

	public void setChat(final long chat) {
		this.chat = chat;
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
