/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ChatMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the table chatmining. 
 * @author Sebastian Schwarzrock
 */
public class ChatMining implements IMappingClass {

	private long id;
	private String title;
	private String description;
	private long chatTime;
	private CourseMining course;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof ChatMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ChatMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}
	
	public CourseMining getCourse() {
		return this.course;
	}

	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChat(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChat(this);
		}
	}

	private Set<ChatLogMining> chatLogs = new HashSet<ChatLogMining>();

	@Override
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public long getChatTime() {
		return this.chatTime;
	}

	public void setChatTime(final long chatTime) {
		this.chatTime = chatTime;
	}

	public Set<ChatLogMining> getChatLogs() {
		return this.chatLogs;
	}

	public void setChatLogs(final Set<ChatLogMining> chatLogs) {
		this.chatLogs = chatLogs;
	}

	public void addChatLog(final ChatLogMining chatLog) {
		this.chatLogs.add(chatLog);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
