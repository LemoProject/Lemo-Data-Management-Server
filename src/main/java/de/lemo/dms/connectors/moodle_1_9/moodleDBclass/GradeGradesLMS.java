/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/GradeGradesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/Grade_grades_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9.moodleDBclass;

/**
 * Mapping class for table GradeGrades.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class GradeGradesLMS {

	private long id;
	private long userid;
	private long itemid;
	private Double rawgrade;
	private Double finalgrade;
	private Long timecreated;
	private Long timemodified;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getItemid() {
		return this.itemid;
	}

	public void setItemid(final long itemid) {
		this.itemid = itemid;
	}

	public Double getRawgrade() {
		return this.rawgrade;
	}

	public void setRawgrade(final Double rawgrade) {
		this.rawgrade = rawgrade;
	}

	public Double getFinalgrade() {
		return this.finalgrade;
	}

	public void setFinalgrade(final Double finalgrade) {
		this.finalgrade = finalgrade;
	}

	public Long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(final Long timecreated) {
		this.timecreated = timecreated;
	}

	public Long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final Long timemodified) {
		this.timemodified = timemodified;
	}
}
