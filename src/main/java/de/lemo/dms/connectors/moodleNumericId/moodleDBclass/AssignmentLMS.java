/**
 * File ./src/main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/AssignmentLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table Assignment.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class AssignmentLMS {

	private long id;
	private long course;
	private String name;
	private String assignmenttype;
	private long timemodified;
	private long timeavailable;
	private long timedue;
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAssignmenttype() {
		return this.assignmenttype;
	}

	public void setAssignmenttype(final String assignmenttype) {
		this.assignmenttype = assignmenttype;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public long getTimeavailable() {
		return this.timeavailable;
	}

	public void setTimeavailable(final long timeavailable) {
		this.timeavailable = timeavailable;
	}

	public long getTimedue() {
		return this.timedue;
	}

	public void setTimedue(final long timedue) {
		this.timedue = timedue;
	}
}
