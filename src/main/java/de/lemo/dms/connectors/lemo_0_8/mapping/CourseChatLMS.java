/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseChatLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseChatLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.lemo_0_8.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;

/** 
 * This class represents the relationship between the courses and chat. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_chat")
public class CourseChatLMS{
	
	private long id;
	private long course;
	private long chat;
	private long platform;

	
	@Id
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="course_id")
	public long getCourse() {
		return course;
	}
	
	public void setCourse(long course) {
		this.course = course;
	}
	
	@Column(name="platform")
	public long getPlatform() {
		return platform;
	}
	
	public void setPlatform(long platform) {
		this.platform = platform;
	}

	@Column(name="chat_id")
	public long getChat() {
		return chat;
	}

	public void setChat(long chat) {
		this.chat = chat;
	}
}
