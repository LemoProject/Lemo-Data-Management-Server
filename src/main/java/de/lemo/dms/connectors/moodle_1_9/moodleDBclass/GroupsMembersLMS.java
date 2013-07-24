/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/GroupsMembersLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/Groups_members_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9.moodleDBclass;

/**
 * Mapping class for table GroupsMembers.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class GroupsMembersLMS {

	private long id;
	private long groupid;
	private long userid;
	private long timeadded;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getGroupid() {
		return this.groupid;
	}

	public void setGroupid(final long groupid) {
		this.groupid = groupid;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getTimeadded() {
		return this.timeadded;
	}

	public void setTimeadded(final long timeadded) {
		this.timeadded = timeadded;
	}
}
