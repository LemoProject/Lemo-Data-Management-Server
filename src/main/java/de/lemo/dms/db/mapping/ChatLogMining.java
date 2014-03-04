/**
 * File ./src/main/java/de/lemo/dms/db/mapping/ChatLogMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/ChatLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILogMining;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/** 
 * This class represents the table chatlog. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "chat_log")
public class ChatLogMining implements IMappingClass, ILogMining {

	private long id;
	private LearningObject chat;
	private UserMining user;
	private String message;
	private long timestamp;
	private CourseMining course;
	private Long duration;
	private Long platform;
	private static final Long PREFIX = 19L;

	@Override
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public CourseMining getCourse() {
		return this.course;
	}

	public void setCourse(final CourseMining course) {
		this.course = course;
	}
	
	@Override
	public int compareTo(final ILogMining o) {
		ILogMining s;
		try {
			s = o;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.timestamp > s.getTimestamp()) {
				return 1;
			}
			if (this.timestamp < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (o != null) {} else return false;
		if (!(o instanceof ChatLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ChatLogMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}
	
	@Override
	public void setDuration(final Long duration)
	{
		this.duration = duration;
	}

	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChatLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChatLog(this);
		}
	}

	@Override
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chat_id")
	public LearningObject getChat() {
		return this.chat;
	}

	public void setChat(final LearningObject chat) {
		this.chat = chat;
	}

	@Override
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public UserMining getUser() {
		return this.user;
	}

	public void setUser(final UserMining user) {
		this.user = user;
	}

	@Override
	@Id
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	@Lob
	@Column(name="message")
	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addChatLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addChatLog(this);
		}
	}

	public void setChat(final long chat, final Map<Long, LearningObject> chatMining,
			final Map<Long, LearningObject> oldChatMining)
	{

		if (chatMining.get(chat) != null)
		{
			this.chat = chatMining.get(chat);
			chatMining.get(chat).addChatLog(this);
		}
		if ((this.chat == null) && (oldChatMining.get(chat) != null))
		{
			this.chat = oldChatMining.get(chat);
			oldChatMining.get(chat).addChatLog(this);
		}
	}

	

	@Override
	@Transient
	public String getAction() {
		return "chat";
	}

	@Override
	@Transient
	public String getTitle() {
		return this.chat.getTitle();
	}

	@Override
	@Transient
	public Long getLearnObjId() {
		return this.getChat().getId();
	}

	@Override
	@Column(name="duration")
	public Long getDuration() {
		return this.duration;
	}

	@Override
	@Transient
	public Long getPrefix() {
		return PREFIX;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
