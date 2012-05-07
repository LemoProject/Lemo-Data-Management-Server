package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListRoleObject;
import de.lemo.dms.processing.resulttype.RoleObject;

@QuestionID("userroles")
public class QUserRoles extends Question{

	@Override
	protected List<ParameterMetaData<?>> createParamMetaData() {
		List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();        
        return parameters;
	}
	
	
	@GET
    public ResultListRoleObject getUserRoles() {
	
		ResultListRoleObject res = null;
		
		//Set up db-connection
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		@SuppressWarnings("unchecked")
        ArrayList<RoleMining> roleMining = (ArrayList<RoleMining>) dbHandler.performQuery(EQueryType.HQL, "from RoleMining");
		
		ArrayList<RoleObject> roles = new ArrayList<RoleObject>();
		for(int i= 0; i < roleMining.size(); i++)
		{
			RoleObject ro = new RoleObject(roleMining.get(i).getId(), roleMining.get(i).getName());
			roles.add(ro);
		}
		
		res = new ResultListRoleObject(roles);
		return res;
		
	}

}
