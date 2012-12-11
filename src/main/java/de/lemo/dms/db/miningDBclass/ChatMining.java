package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class ChatMining implements IMappingClass {

	
	private long id;
	private String title;
	private String description;
	private long chattime;
	private CourseMining course;
	private Long platform;
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ChatMining))
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

	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		
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
	
	private Set<ChatLogMining> chat_log = new HashSet<ChatLogMining>();
	
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
	public long getChattime() {
		return chattime;
	}
	public void setChattime(long chattime) {
		this.chattime = chattime;
	}
	public Set<ChatLogMining> getChat_log() {
		return chat_log;
	}
	public void setChat_log(Set<ChatLogMining> chat_log) {
		this.chat_log = chat_log;
	}
	public void addChat_log(ChatLogMining chat_log_add){	
		chat_log.add(chat_log_add);
	}



	public Long getPlatform() {
		return platform;
	}



	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
}
