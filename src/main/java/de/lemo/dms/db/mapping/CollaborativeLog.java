/**
 * File ./src/main/java/de/lemo/dms/db/mapping/CollaborativeLog.java
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
 * File ./main/java/de/lemo/dms/db/mapping/CollaborativeLog.java
 * Date 2014-02-04
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.db.mapping.abstractions.IMapping;

/**
 * This class represents the log table for the collaborative_Log objects.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_collaborative_log")
public class CollaborativeLog implements IMapping, ILog{

	private long id;
	private Course course;
	private User user;
	private Resource resource;
	private long timestamp;
	private String text;
	private Resource parent;
	private Long platform;
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof CollaborativeLog)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CollaborativeLog)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	@Override
	public int compareTo(final ILog arg0) {
		ILog s;
		try {
			s = arg0;
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
	
	@Id
	public long getId() {
		return id;
	}
	
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public Course getCourse() {
		return course;
	}
	
	
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="resource_id")
	public Resource getResource() {
		return resource;
	}
	
	
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	
	
	@Column(name="timestamp")
	public long getTimestamp() {
		return timestamp;
	}
	
	
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	@Column(name="platform")
	public Long getPlatform() {
		return platform;
	}
	
	
	
	public void setPlatform(Long platform) {
		this.platform = platform;
	}


	@Lob
	@Column(name="text")
	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public Resource getParent() {
		return parent;
	}



	public void setParent(Resource parent) {
		this.parent = parent;
	}
	
}
