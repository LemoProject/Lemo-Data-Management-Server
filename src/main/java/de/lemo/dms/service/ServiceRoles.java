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

@Path("roles")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRoles extends BaseService {

    @GET
    public ResultListRoleObject getUserRoles() {

        IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();

        @SuppressWarnings("unchecked")
        ArrayList<RoleMining> roleMining = (ArrayList<RoleMining>) dbHandler.performQuery(session, EQueryType.HQL,
            "from RoleMining");

        ArrayList<RoleObject> roles = new ArrayList<RoleObject>();
        for(RoleMining role : roleMining) {
            roles.add(new RoleObject(role.getId(), role.getName()));
        }

        dbHandler.closeSession(session);
        return new ResultListRoleObject(roles);
    }

}
