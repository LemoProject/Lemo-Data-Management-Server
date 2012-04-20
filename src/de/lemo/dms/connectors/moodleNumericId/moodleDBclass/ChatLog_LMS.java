package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class ChatLog_LMS {

	private long id;
	private long chat_id;
	private long userid;
	private String message;
	private long timestamp;
	
	
	
	
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getChat_id() {
		return chat_id;
	}
	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
