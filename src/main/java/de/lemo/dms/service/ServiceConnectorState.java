/**
 * File ./main/java/de/lemo/dms/service/ServiceGetConnectorState.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.EConnectorState;

/**
 * return the state of the connector
 * ready = ready to load data
 * progress = load data is in progress
 * noconnector = no connector is selected
 * noconfiguration = something is wrong with the configuration
 * 
 * @author Boris Wenzlaff
 */
@Path("/getconnectorstate")
public class ServiceConnectorState {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getConnectrorStateJson() {
		logger.info("call for service: getConnectrorStateJson");
		final ConnectorManager cm = ConnectorManager.getInstance();
		final EConnectorState cs = cm.connectorState();
		final JSONObject result = new JSONObject();
		try {
			result.put("state", cs.name());
		} catch (final JSONException e) {
			logger.warn(e.getMessage());
		}
		return result;
	}
}
