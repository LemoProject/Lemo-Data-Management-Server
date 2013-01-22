package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class ChatMining implements IMappingClass {

	
	private long id;
	private String title;
	private String description;
	private long chatTime;
	private CourseMining course;
	private Long platform;
	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof ChatMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ChatMining))
			return true;
		return false;
	}


	
	public CourseMining getCourse() {
		return course;
	}
	public void setCourse(CourseMining course) {
		this.course = course;
	}

	public void setCourse(long course, Map<Long, CourseMining> courseMining, Map<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChat(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChat(this);
		}
	}
	
	private Set<ChatLogMining> chatLogs = new HashSet<ChatLogMining>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public long getChatTime() {
		return chatTime;
	}
	public void setChatTime(long chatTime) {
		this.chatTime = chatTime;
	}
	public Set<ChatLogMining> getChatLogs() {
		return chatLogs;
	}
	public void setChatLogs(Set<ChatLogMining> chatLogs) {
		this.chatLogs = chatLogs;
	}
	public void addChatLog(ChatLogMining chatLog){	
		this.chatLogs.add(chatLog);
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
}
