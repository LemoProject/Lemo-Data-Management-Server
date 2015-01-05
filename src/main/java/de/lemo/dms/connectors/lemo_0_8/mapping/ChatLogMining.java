/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ChatLogMining.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ChatLogMining.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * This class represents the table chatlog. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "chat_log")
public class ChatLogMining{

	private long id;
	private long chat;
	private long user;
	private String message;
	private long timestamp;
	private long course;
	private Long duration;
	private Long platform;
	private static final Long PREFIX = 19L;

	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}
	
		
	public void setDuration(final Long duration)
	{
		this.duration = duration;
	}

	
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name="chat_id")
	public long getChat() {
		return this.chat;
	}

	public void setChat(final long chat) {
		this.chat = chat;
	}


	@Column(name="user_id")
	public long getUser() {
		return this.user;
	}

	public void setUser(final long user) {
		this.user = user;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="message")
	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Column(name="duration")
	public Long getDuration() {
		return this.duration;
	}

	
	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
