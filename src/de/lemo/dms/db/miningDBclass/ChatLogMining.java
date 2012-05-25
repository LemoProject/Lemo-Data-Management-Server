package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;


public class ChatLogMining{

	private long id;
	private ChatMining chat;
	private UserMining user;
	private String message;
	private long timestamp;
	
	
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public ChatMining getChat() {
		return chat;
	}
	public void setChat(ChatMining chat) {
		this.chat = chat;
	}

	public UserMining getUser() {
		return user;
	}
	public void setUser(UserMining user) {
		this.user = user;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addChat_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addChat_log(this);
		}
	}
	
	public void setChat(long chat, HashMap<Long, ChatMining> chatMining, HashMap<Long, ChatMining> oldChatMining) 
	{		
		
		if(chatMining.get(chat) != null)
		{
			this.chat = chatMining.get(chat);
			chatMining.get(chat).addChat_log(this);
		}
		if(this.chat == null && oldChatMining.get(chat) != null)
		{
			this.chat = oldChatMining.get(chat);
			oldChatMining.get(chat).addChat_log(this);
		}
	}
	
}
