/**
 * File ./src/main/java/de/lemo/dms/db/mapping/ChatMining.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/db/mapping/ChatMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/** 
 * This class represents the table chatmining. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "chat")
public class ChatMining implements IMappingClass, ILearningObject {

	private long id;
	private String title;
	private String description;
	private long chatTime;
	private CourseMining course;
	private Long platform;
	private static final Long PREFIX = 19L;
	

	private Set<ChatLogMining> chatLogs = new HashSet<ChatLogMining>();
	private Set<CourseChatMining> courseChats = new HashSet<CourseChatMining>();
	
	@Transient
	public Long getPrefix()
	{
		return PREFIX;
	}
	
	/**
	 * standard setter for the attribute course_chat
	 * 
	 * @param courseChat
	 *            this entry will be added to the list of course_chat in this assignment
	 */
	public void addCourseChat(final CourseChatMining courseChat) {
		this.courseChats.add(courseChat);
	}

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
	
	@Transient
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



	@Override
	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="title", length=1000)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name="description", length=1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(name="chattime")
	public long getChatTime() {
		return this.chatTime;
	}

	public void setChatTime(final long chatTime) {
		this.chatTime = chatTime;
	}

	@OneToMany(mappedBy="chat")
	public Set<ChatLogMining> getChatLogs() {
		return this.chatLogs;
	}
	
	@OneToMany(mappedBy="chat")
	public Set<CourseChatMining> getCourseChats() {
		return this.courseChats;
	}

	public void setChatLogs(final Set<ChatLogMining> chatLogs) {
		this.chatLogs = chatLogs;
	}
	
	public void setCourseChats(final Set<CourseChatMining> courseChats) {
		this.courseChats = courseChats;
	}

	public void addChatLog(final ChatLogMining chatLog) {
		this.chatLogs.add(chatLog);
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
