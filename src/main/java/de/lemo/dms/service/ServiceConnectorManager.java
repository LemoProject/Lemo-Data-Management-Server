/**
 * File ./src/main/java/de/lemo/dms/service/ServiceConnectorManager.java
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
 * File ./main/java/de/lemo/dms/service/ServiceGetConnectorState.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import com.google.common.collect.Lists;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.EConnectorManagerState;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.service.responses.ResourceNotFoundException;
import de.lemo.dms.service.servicecontainer.SCConnector;
import de.lemo.dms.service.servicecontainer.SCConnectorManagerState;
import de.lemo.dms.service.servicecontainer.SCConnectors;

/**
 * Return the state of the connector update process.
 * 
 * @see EConnectorManagerState
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
@Path("/connectors")
public class ServiceConnectorManager {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SCConnectors connectorListJson() {
		final SCConnectors rs = new SCConnectors();
		rs.setConnectors(this.getConnectors());
		return rs;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String connectorListHtml() {
		final StringBuilder result = new StringBuilder();
		result.append("<html><title>Available Connectors</title><body><h2>Available Connectors</h2><ul>");
		for (final SCConnector s : this.getConnectors()) {
			result.append("<li>").append(s.toString()).append("</li>");
		}
		result.append("</ul></body></html>");
		return result.toString();
	}

	@GET
	@Path("/state")
	@Produces(MediaType.APPLICATION_JSON)
	public SCConnectorManagerState state() {
		ConnectorManager manager = ConnectorManager.getInstance();
		SCConnector updatingConnector = null;
		if (manager.getUpdatingConnector() != null) {
			updatingConnector = new SCConnector(manager.getUpdatingConnector());
		}
		EConnectorManagerState state = manager.connectorState();
		logger.debug("Connector state: " + state);
		return new SCConnectorManagerState(state, updatingConnector);
	}

	/**
	 * @param connectorId
	 *            the connector to update, provided in the paths
	 * @return a JSON object containing the state of the update process.
	 * @throws JSONException
	 */
	@POST
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean update(@PathParam("id") final Long connectorId) {

		final ConnectorManager manager = ConnectorManager.getInstance();
		final IConnector connector = manager.getConnectorById(connectorId);
		if (connector == null) {
			throw new ResourceNotFoundException("No connector with id '" + connectorId + "' found");
		}

		final boolean updating = manager.startUpdateData(connector);
		logger.info("Connector " + connector + ", update started: " + updating);
		return updating;
	}

	private List<SCConnector> getConnectors() {
		final List<SCConnector> result = Lists.newArrayList();
		for (final IConnector connector : ConnectorManager.getInstance().getAvailableConnectors()) {
			result.add(new SCConnector(connector));
		}
		return result;
	}
}
