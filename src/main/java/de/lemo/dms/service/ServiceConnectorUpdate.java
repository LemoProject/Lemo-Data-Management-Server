/**
 * File ./main/java/de/lemo/dms/service/ServiceLoadWholeDatabase.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.IConnector;

/**
 * Service to run platform connector updates. Only a single update can run at any time.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
@Path("/connectors/{id}/update")
public class ServiceConnectorUpdate {

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param connectorId
	 *            the connector to update
	 * @return a JSON object containing the state of the update process.
	 * @throws JSONException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject update(@PathParam("id") final Long connectorId) throws JSONException {

		final ConnectorManager cm = ConnectorManager.getInstance();
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(connectorId);
		if (connector == null) {
			final JSONObject rs = new JSONObject();
			rs.put("error", "invalid connector id");
			return rs;
		}
		logger.info("Selected connector " + connector);
		final boolean updating = cm.startUpdateData(connector);

		final JSONObject rs = new JSONObject();
		rs.put("updating", updating);

		return rs;
	}
}
