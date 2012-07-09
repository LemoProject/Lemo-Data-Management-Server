package de.lemo.dms.connectors.clix2010.clixDBClass;

public class ChatProtocol {

	private Long id;
	private Long chatroom;
	private Long person;
	private String chatSource;
	private String lastUpdated;
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getChatroom() {
		return chatroom;
	}



	public void setChatroom(Long chatroom) {
		this.chatroom = chatroom;
	}



	public Long getPerson() {
		return person;
	}



	public void setPerson(Long person) {
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
