/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseWikiMining.java
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/CourseWikiMining.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents the relationship between courses and wikis.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_wiki")
public class CourseWikiMining{

	private long id;
	private long course;
	private long wiki;
	private Long platform;
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and wiki
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and wiki
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return a course in which the wiki is used in
	 */
	@Column(name="course_id")
	public long getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the wiki is used in
	 */
	public void setCourse(final long course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute wiki
	 * 
	 * @return the wiki which is used in the course in this entry
	 */
	@Column(name="wiki_id")
	public long getWiki() {
		return this.wiki;
	}

	/**
	 * standard setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the wiki which is used in the course in this entry
	 */
	public void setWiki(final long wiki) {
		this.wiki = wiki;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
