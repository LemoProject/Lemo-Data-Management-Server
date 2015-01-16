/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ResourceLogLMS.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ResourceLogLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the log table for the resource object. */
@Entity
@Table(name = "resource_log")
public class ResourceLogLMS{

	private long id;
	private long resource;
	private long user;
	private long course;
	private String action;
	private long timestamp;
	private Long duration;
	private Long platform;

	/**
	 * Gets the duration of the access.
	 * 
	 * @return The duration of the access.
	 */
	@Column(name="duration")
	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the log entry
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the log entry
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the action did occur
	 */
	@Column(name="timestamp")
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp the action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final long course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	/**
	 * standard getter for the attribute action
	 * 
	 * @return the action which occur
	 */
	@Column(name="action")
	public String getAction() {
		return this.action;
	}

	/**
	 * standard setter for the attribute action
	 * 
	 * @param action
	 *            the action which occur
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * standard setter for the attribute resource
	 * 
	 * @param resource
	 *            the resource with which was interacted
	 */
	public void setResource(final long resource) {
		this.resource = resource;
	}

	/**
	 * standard getter for the attribute resource
	 * 
	 * @return the resource with which was interacted
	 */
	@Column(name="resource_id")
	public long getResource() {
		return this.resource;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the resource
	 */
	public void setUser(final long user) {
		this.user = user;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the resource
	 */
	@Column(name="user_id")
	public long getUser() {
		return this.user;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
