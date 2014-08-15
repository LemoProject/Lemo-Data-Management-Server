/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/UserPathNode.java
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
 * File ./main/java/de/lemo/dms/processing/resulttype/UserPathNode.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

/**
 * represent a node in the path of a user
 * @author Sebastian Schwarzrock
 *
 */
public class UserPathNode {

	private String name;
	private String title;
	private Long value;
	private Long group;
	private Long pathId;
	private String type;
	private Long totalRequests;
	private Long totalUsers;

	public Long getTotalRequests() {
		return this.totalRequests;
	}

	public void setTotalRequests(final Long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public Long getTotalUsers() {
		return this.totalUsers;
	}

	public void setTotalUsers(final Long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Long getPathId() {
		return this.pathId;
	}

	public void setPathId(final Long pathId) {
		this.pathId = pathId;
	}

	public UserPathNode() {
	}

	public UserPathNode(final UserPathObject path) {
		this.name = path.getTitle();
		if ((this.name == null) || this.name.isEmpty()) {
			this.name = "?";
		}
		this.value = path.getWeight();
		this.group = path.getGroup();
		this.pathId = path.getPathId();
		this.type = path.getType();
		this.totalRequests = path.getTotalRequests();
		this.totalUsers = path.getTotalUsers();
	}

	public UserPathNode(final UserPathObject path, final Boolean directedGraph) {
		this.name = path.getId();
		this.title = path.getTitle();
		if ((this.name == null) || this.name.isEmpty()) {
			this.name = "?";
		}
		this.value = path.getWeight();
		this.group = path.getGroup();
		this.pathId = path.getPathId();
		this.type = path.getType();
		this.totalRequests = path.getTotalRequests();
		this.totalUsers = path.getTotalUsers();
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	public Long getValue() {
		return this.value;
	}

	public void setValue(final Long value) {
		this.value = value;
	}

	public Long getGroup() {
		return this.group;
	}

	public void setGroup(final Long group) {
		this.group = group;
	}

}
