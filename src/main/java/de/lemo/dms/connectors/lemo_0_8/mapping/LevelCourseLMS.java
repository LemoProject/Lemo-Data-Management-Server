/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/LevelCourseLMS.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/LevelCourseLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represanting an object of the level of an hierarchy
 * @author Sebastian Schwarzrock
 *
 */
@Entity
@Table(name = "level_course")
public class LevelCourseLMS{

	private long id;
	private long course;
	private long level;
	private Long platform;


	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between department and resource
	 */

	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between department and resource
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute
	 * 
	 * @return a department in which the resource is used
	 */
	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	@Column(name="level_id")
	public long getLevel() {
		return this.level;
	}

	public void setLevel(final long degree) {
		this.level = degree;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
