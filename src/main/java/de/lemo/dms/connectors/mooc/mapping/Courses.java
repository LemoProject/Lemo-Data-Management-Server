/**
 * File ./src/main/java/de/lemo/dms/connectors/iversity/mapping/CourseLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/iversity/mapping/Course_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.mooc.mapping;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapping class for table Course.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "courses")
public class Courses {

	private long id;
	private String subtitle;
	private String title;
	private Date startDate;
	private Date timeCreated;
	private Date timeModified;
	private String description;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Lob
	@Column(name="subtitle")
	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(final String subtitle) {
		this.subtitle = subtitle;
	}

	@Column(name = "created_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeCreated() {
		return this.timeCreated;
	}

	public void setTimeCreated(final Date timecreated) {
		this.timeCreated = timecreated;
	}

	@Column(name = "updated_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeModified() {
		return this.timeModified;
	}

	public void setTimeModified(final Date timemodified) {
		this.timeModified = timemodified;
	}

	

	@Column(name = "start_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startdate) {
		this.startDate = startdate;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * @return the description
	 */
	@Lob
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
