/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ContextLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Context_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table Context.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ContextLMS {

	private long id;
	private long contextlevel;
	private long instanceid;
	private String path;
	private long depth;

	public long getDepth() {
		return this.depth;
	}

	public void setDepth(final long depth) {
		this.depth = depth;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setContextlevel(final long contextlevel) {
		this.contextlevel = contextlevel;
	}

	public long getContextlevel() {
		return this.contextlevel;
	}

	public void setInstanceid(final long instanceid) {
		this.instanceid = instanceid;
	}

	public long getInstanceid() {
		return this.instanceid;
	}
}
