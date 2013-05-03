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
import de.lemo.dms.db.miningDBclass.RoleMining;
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
		final ArrayList<RoleMining> roleMining = (ArrayList<RoleMining>) dbHandler.performQuery(session,
				EQueryType.HQL,
				"from RoleMining");

		final ArrayList<RoleObject> roles = new ArrayList<RoleObject>();
		for (final RoleMining role : roleMining) {
			roles.add(new RoleObject(role.getId(), role.getName()));
		}

		session.close();
		return new ResultListRoleObject(roles);
	}

}
