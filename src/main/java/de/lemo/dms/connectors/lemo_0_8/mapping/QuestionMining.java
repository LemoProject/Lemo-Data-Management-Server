/**
  * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuestionMining.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuestionMining.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the table question. */
@Entity
@Table(name = "question")
public class QuestionMining{

	private long id;
	private String title;
	private String text;
	private String type;
	private long timeCreated;
	private long timeModified;
	private Long platform;	
		
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the question
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the question
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the question
	 */
	@Column	(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the question
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute text
	 * 
	 * @return the text of the question
	 */
	@Column	(name="text")
	public String getText() {
		return this.text;
	}

	/**
	 * standard setter for the attribute text
	 * 
	 * @param text
	 *            the text of the question
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * standard getter for the attribute type
	 * 
	 * @return the type of the question
	 */
	@Column	(name="type")
	public String getType() {
		return this.type;
	}

	/**
	 * standard setter for the attribute type
	 * 
	 * @param type
	 *            the type of the question
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the question was created
	 */
	@Column	(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timecreated
	 *            the timestamp when the question was created
	 */
	public void setTimeCreated(final long timecreated) {
		this.timeCreated = timecreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the question was changed the last time
	 */
	@Column	(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the question was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	
	@Column	(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
