package de.lemo.dms.connectors.clix2010.clixDBClass;

public class ChatProtocol {

	private long id;
	private long chatroom;
	private long person;
	private String chatSource;
	private String lastUpdated;
	
	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getChatroom() {
		return chatroom;
	}



	public void setChatroom(long chatroom) {
		this.chatroom = chatroom;
	}



	public long getPerson() {
		return person;
	}



	public void setPerson(long person) {
		this.person = person;
	}



	public String getChatSource() {
		return chatSource;
	}



	public void setChatSource(String chatSource) {
		this.chatSource = chatSource;
	}



	public String getLastUpdated() {
		return lastUpdated;
	}



	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



	public ChatProtocol()
	{}
	
	
}
