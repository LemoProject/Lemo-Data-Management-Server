package de.lemo.dms.service;

import javax.ws.rs.*;
import de.lemo.dms.core.ConnectorManager;
import de.lemo.dms.service.servicecontainer.SCConnectors;

/**
 * REST Webservice for the available connectors of the DMS
 * @author Boris Wenzlaff
 *
 */

@Path("/getavailableconnectors")
public class ServiceGetAvailableConnectors extends ServiceBaseService{
	
	@GET @Produces("application/json")
	public SCConnectors getAvailableConnecttorsJson() {
		SCConnectors rs = new SCConnectors();
		rs.setConnectors(ConnectorManager.getInstance().getAvailableConnectorsList());
		super.logger.info("call for service: getAvailableConnecttorsJson");
		return rs;
	}
	
	@GET @Produces("text/html")
	public String getAvailableConnecttorsHtml() {
		StringBuilder result = new StringBuilder();
		ConnectorManager cm = ConnectorManager.getInstance();
		result.append("<html><title>Available Connectors</title><body><h2>Available Connectors</h2><ul>");
		for(String s : cm.getAvailableConnectorsSet()) {
			result.append("<li>" + s + "</li>");		
		}
		result.append("</ul></body></html>");
		super.logger.info("call for service: getAvailableConnecttorsHtml");
		return result.toString();
	}
}
