/**
 * File ./main/java/de/lemo/dms/service/ServiceLoadWholeDatabase.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.IConnector;

/**
 * Service to Load the whole database
 * 
 * @author Boris Wenzlaff
 */
@Path("/loadwholedatabase")
public class ServiceLoadWholeDatabase {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject loadWholeDatabase(@QueryParam("connector") final Long connectorId) throws JSONException {
		logger.info("call for service: loadWholeDatabase");

		final ConnectorManager cm = ConnectorManager.getInstance();
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(connectorId);
		if (connector == null) {
			final JSONObject rs = new JSONObject();
			rs.put("error", "invalid connector id");
			return rs;
		}
		logger.info("Selected connector " + connector);
		final boolean loaded = cm.startUpdateData(connector);

		final JSONObject rs = new JSONObject();

		rs.put("loaded", loaded);
		rs.put("loaded", loaded);

		return rs;
	}
}
