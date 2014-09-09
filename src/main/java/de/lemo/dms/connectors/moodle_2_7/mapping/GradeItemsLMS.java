/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/GradeItemsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Grade_items_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table GradeItems.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "mdl_grade_items")
public class GradeItemsLMS {

	private long id;
	private long courseid;
	private String itemname;
	private String itemmodule;
	private Long iteminstance;
	private double grademax;
	private long timecreated;
	private long timemodified;

	@Column(name="iteminstance")
	public Long getIteminstance() {
		return this.iteminstance;
	}

	public void setIteminstance(final Long iteminstance) {
		this.iteminstance = iteminstance;
	}
	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="itemmodule")
	public String getItemmodule() {
		return this.itemmodule;
	}

	public void setItemmodule(final String itemmodule) {
		this.itemmodule = itemmodule;
	}

	@Column(name="timecreated")
	public long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	@Column(name="courseid")
	public long getCourseid() {
		return this.courseid;
	}

	public void setCourseid(final long courseid) {
		this.courseid = courseid;
	}

	@Column(name="itemname")
	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(final String itemname) {
		this.itemname = itemname;
	}

	public void setGrademax(final double grademax) {
		this.grademax = grademax;
	}

	@Column(name="grademax")
	public double getGrademax() {
		return this.grademax;
	}

}
