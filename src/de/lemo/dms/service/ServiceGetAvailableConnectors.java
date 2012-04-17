package de.lemo.dms.service;

import java.text.SimpleDateFormat;
import javax.ws.rs.*;

import de.lemo.dms.core.ConnectorManager;

/**
 * REST Webservice for the available Connectors of the DMS
 * @author Boris Wenzlaff
 *
 */

@Path("/getavailableconnectors")
public class ServiceGetAvailableConnectors extends ServiceBaseService{
	
	public ServiceGetAvailableConnectors() {
		super();
	}
	
	@GET @Produces("application/xml")
	public ResultXmlList getAvailableConnecttors() {
		ResultXmlList result = new ResultXmlList();
		ConnectorManager cm = ConnectorManager.getInstance();
		result.setResultList(cm.getAvailableConnectors());
		return result;
	}
	
	@GET @Produces("text/html")
	public String getAvailableConnecttorsHtml() {
		StringBuilder result = new StringBuilder();
		ConnectorManager cm = ConnectorManager.getInstance();
		result.append("<html><title>Available Connectors</title><body><h2>Available Connectors</h2><ul>");
		for(String s : cm.getAvailableConnectors()) {
			result.append("<li>" + s + "</li>");		
		}
		result.append("</ul></body></html>");
		return result.toString();
	}
}
