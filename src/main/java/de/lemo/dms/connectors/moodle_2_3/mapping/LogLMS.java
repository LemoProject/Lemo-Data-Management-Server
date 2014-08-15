/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/mapping/LogLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/mapping/Log_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Log.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_log")
public class LogLMS {

	private long id;
	private long time;
	private long userid;
	private long course;
	private long cmid;
	private String module;
	private String action;
	private String info;

	@Column(name="module")
	public String getModule() {
		return this.module;
	}

	public void setModule(final String module) {
		this.module = module;
	}

	@Column(name="action")
	public String getAction() {
		return this.action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	@Column(name="info")
	public String getInfo() {
		return this.info;
	}

	public void setInfo(final String info) {
		this.info = info;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="time")
	public long getTime() {
		return this.time;
	}

	public void setTime(final long time) {
		this.time = time;
	}

	@Column(name="userid")
	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	@Column(name="course")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public void setCmid(final long cmid) {
		this.cmid = cmid;
	}

	@Column(name="cmid")
	public long getCmid() {
		return this.cmid;
	}
}
