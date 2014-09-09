/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/CourseModulesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Course_Modules_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table CourseModules.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_course_modules")
public class CourseModulesLMS {

	private long id;
	private long course;
	private long module;
	private long instance;

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="course")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	@Column(name="module")
	public long getModule() {
		return this.module;
	}

	public void setModule(final long module) {
		this.module = module;
	}

	@Column(name="instance")
	public long getInstance() {
		return this.instance;
	}

	public void setInstance(final long instance) {
		this.instance = instance;
	}

}
