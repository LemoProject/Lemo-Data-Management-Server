package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import de.lemo.dms.connectors.EConnectorState;
import de.lemo.dms.core.ConnectorManager;

/**
 * return the state of the connector 
 * ready = ready to load data 
 * progress = load data is in progress 
 * noconnector = no connector is selected 
 * noconfiguration = something is wrong with the configuration
 * 
 * @author Boris Wenzlaff
 * 
 */
@Path("/getconnectorstate")
public class ServiceGetConnectorState extends BaseService {
	
	@GET @Produces(MediaType.APPLICATION_JSON)
	public JSONObject getConnectrorStateJson() {
		super.logger.info("call for service: getConnectrorStateJson");
		ConnectorManager cm = ConnectorManager.getInstance();
		EConnectorState cs = cm.connectorState();
		JSONObject result = new JSONObject();
		try {
			result.put("state", cs.name());
		}
		catch (JSONException e) {
			super.logger.warn(e.getMessage());
		}
		return result;
	}
}
