package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class ChatLog_LMS {

	private long id;
	private long chat_Id;
	private long user_Id;
	private String message;
	private long timestamp;
	
	
	
	
	public long getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(long userid) {
		this.user_Id = userid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getChat_Id() {
		return chat_Id;
	}
	public void setChat_Id(long chat_id) {
		this.chat_Id = chat_id;
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
