/**
 * File ./src/main/java/de/lemo/dms/service/ServiceRoles.java
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
 * File ./main/java/de/lemo/dms/service/ServiceRoles.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.processing.resulttype.ResultListRoleObject;
import de.lemo.dms.processing.resulttype.RoleObject;

/**
 * Service to get the defined roles
 * 
 * @author Sebastian Schwarzrock
 */
@Path("roles")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRoles {

	@GET
	public ResultListRoleObject getUserRoles() {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		@SuppressWarnings("unchecked")
		final ArrayList<Role> roleMining = (ArrayList<Role>) dbHandler.performQuery(session,
				EQueryType.HQL,
				"from RoleMining");

		final ArrayList<RoleObject> roles = new ArrayList<RoleObject>();
		for (final Role role : roleMining) {
			roles.add(new RoleObject(role.getId(), role.getTitle()));
		}

		session.close();
		return new ResultListRoleObject(roles);
	}

}
