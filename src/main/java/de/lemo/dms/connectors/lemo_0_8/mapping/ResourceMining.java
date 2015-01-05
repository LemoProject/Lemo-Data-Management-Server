/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ResourceMining.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ResourceMining.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the table resource. */
@Entity
@Table(name = "resource")
public class ResourceMining{

	/** The id. */
	private long id;
	/** The type. */
	private String type;
	/** The title. */
	private String title;
	/** The timeCreated. */
	private long timeCreated;
	/** The timeModified. */
	private long timeModified;
	/** The difficulty. */
	private String difficulty;
	/** The processing time. */
	private long processingTime;
	/** The url. */
	private String url;
	/** The position. */
	private long position;
	private Long platform;

	
	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	@Column(name="url")
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	@Column(name="position")
	public long getPosition() {
		return this.position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param position
	 *            the new position
	 */
	public void setPosition(final long position) {
		this.position = position;
	}

	/**
	 * Gets the estimated difficulty of the resource.
	 * 
	 * @return The estimated difficulty of the resource.
	 */
	@Column(name="difficulty")
	public String getDifficulty() {
		return this.difficulty;
	}

	/**
	 * Sets the estimated difficulty of the resource.
	 * 
	 * @param difficulty
	 *            The estimated difficulty of the resource.
	 */
	public void setDifficulty(final String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Gets the recommended processing time of the resource.
	 * 
	 * @return The recommended processing time of the resource.
	 */
	@Column(name="processingtime")
	public long getProcessingTime() {
		return this.processingTime;
	}

	/**
	 * Sets the recommended processing time of the resource.
	 * 
	 * @param processingTime
	 *            the recommended processing time of the resource.
	 */
	public void setProcessingTime(final long processingTime) {
		this.processingTime = processingTime;
	}

	/**
	 * standard getter for the attribute id.
	 * 
	 * @return the identifier of the resource
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id.
	 * 
	 * @param id
	 *            the identifier of the resource
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute type.
	 * 
	 * @return the type of the resource
	 */
	@Column(name="type")
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute type.
	 * 
	 * @param type
	 *            the type of the resource
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute timeCreated.
	 * 
	 * @return the timestamp when the resource was created
	 */
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timeCreated.
	 * 
	 * @param timeCreated
	 *            the timestamp when the resource was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timeModified.
	 * 
	 * @return the timestamp when the resource was changed the last time
	 */	
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timeModified.
	 * 
	 * @param timeModified
	 *            the timestamp when the resource was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute title.
	 * 
	 * @param title
	 *            the title of the resource
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title.
	 * 
	 * @return the title of the resource
	 */
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
