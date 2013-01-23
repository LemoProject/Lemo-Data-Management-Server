package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;


public class ChatLogMining implements IMappingClass, ILogMining{

	private long id;
	private ChatMining chat;
	private UserMining user;
	private String message;
	private long timestamp;
	private CourseMining course;
	private Long duration;
	private Long platform;
	
	public CourseMining getCourse() {
		return course;
	}

	public void setCourse(CourseMining course) {
		this.course = course;
	}

	@Override
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ChatLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ChatLogMining))
			return true;
		return false;
	}
	
	public void setDuration(Long duration)
	{
		this.duration = duration;
	}
	
	public void setCourse(long course, Map<Long, CourseMining> courseMining, Map<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChatLog(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChatLog(this);
		}
	}
	
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
	
	public void setUser(long user, Map<Long, UserMining> userMining, Map<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addChatLog(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addChatLog(this);
		}
	}
	
	public void setChat(long chat, Map<Long, ChatMining> chatMining, Map<Long, ChatMining> oldChatMining) 
	{		
		
		if(chatMining.get(chat) != null)
		{
			this.chat = chatMining.get(chat);
			chatMining.get(chat).addChatLog(this);
		}
		if(this.chat == null && oldChatMining.get(chat) != null)
		{
			this.chat = oldChatMining.get(chat);
			oldChatMining.get(chat).addChatLog(this);
		}
	}

	@Override
	public int compareTo(ILogMining o) {
		ILogMining s;
		try{
			s = o;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.timestamp > s.getTimestamp())
			return 1;
		if(this.timestamp < s.getTimestamp())
			return -1;
		return 0;
	}

	@Override
	public String getAction() {
		return "chat";
	}

	@Override
	public String getTitle() {
		return this.chat.getTitle();
	}

	@Override
	public Long getLearnObjId() {
		return this.getChat().getId();
	}

	
	public Long getDuration() {
		return duration;
	}

	@Override
	public Long getPrefix() {
		return 19L;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
}
