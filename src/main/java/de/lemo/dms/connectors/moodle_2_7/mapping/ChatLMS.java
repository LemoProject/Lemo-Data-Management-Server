/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/ChatLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Chat_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Chat.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "mdl_chat")
public class ChatLMS {

	private long id;
	private String title;
	private String description;
	private long chattime;
	private long timemodified;
	private long course;

	@Column(name="course")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="name")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name="intro")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(name="chattime")
	public long getChattime() {
		return this.chattime;
	}

	public void setChattime(final long chattime) {
		this.chattime = chattime;
	}
}
