package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

public class ChatLog_LMS {

	private long id;
	private long chatId;
	private long userId;
	private String message;
	private long timestamp;
	
	
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userid) {
		this.userId = userid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getChatId() {
		return chatId;
	}
	public void setChatId(long chat_id) {
		this.chatId = chat_id;
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
