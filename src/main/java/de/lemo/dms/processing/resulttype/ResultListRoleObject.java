/**
 * File ./src/main/java/de/lemo/dms/processing/resulttype/ResultListRoleObject.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListRoleObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for RoleObject which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListRoleObject {

	private List<RoleObject> roles;

	public ResultListRoleObject() {
	}

	public ResultListRoleObject(final List<RoleObject> roles) {
		this.roles = roles;
	}

	public List<RoleObject> getRoles() {
		return this.roles;
	}

	public void setRoles(final List<RoleObject> roles) {
		this.roles = roles;
	}
}
