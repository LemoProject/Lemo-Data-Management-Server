package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.IConnector;

/**
 * load the whole database * @author Boris Wenzlaff
 * 
 */
@Path("/loadwholedatabase")
public class ServiceLoadWholeDatabase extends BaseService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject loadWholeDatabase(@QueryParam("connector") Long connectorId) {
        super.logger.info("call for service: loadWholeDatabase");

        ConnectorManager cm = ConnectorManager.getInstance();
        IConnector connector = ConnectorManager.getInstance().getConnectorById(connectorId);
        boolean loaded = cm.startUpdateData(connector);

        JSONObject rs = new JSONObject();
        try {
            rs.put("loaded", loaded);
            rs.put("loaded", loaded);
        } catch (JSONException e) {
            super.logger.warn(e.getMessage());
        }
        return rs;
    }
}
