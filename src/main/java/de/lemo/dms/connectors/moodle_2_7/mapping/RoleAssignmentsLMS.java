/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/RoleAssignmentsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/Role_assignments_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table RoleAssignments.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */

@Entity
@Table(name = "mdl_role_assignments")
public class RoleAssignmentsLMS {

	private long id;
	private long roleid;
	private long contextid;
	private String userid;
	private long timemodified;
	private long modifierid;

	public void setId(final long id) {
		this.id = id;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setRoleid(final long roleid) {
		this.roleid = roleid;
	}

	@Column(name="roleid")
	public long getRoleid() {
		return this.roleid;
	}

	@Column(name="contextid")
	public long getContextid() {
		return this.contextid;
	}

	public void setContextid(final long contextid) {
		this.contextid = contextid;
	}

	@Column(name="userid")
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public void setModifierid(final long modifierid) {
		this.modifierid = modifierid;
	}

	@Column(name="modifierid")
	public long getModifierid() {
		return this.modifierid;
	}
}
