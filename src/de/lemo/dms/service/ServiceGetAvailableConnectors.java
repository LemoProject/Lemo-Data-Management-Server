package de.lemo.dms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.*;

import de.lemo.dms.core.ConnectorManager;
import de.lemo.dms.service.servicecontainer.SCConnectors;

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
	
	/*@GET @Produces("application/json")
	public ResultXmlList getAvailableConnecttors() {
		ResultXmlList result = new ResultXmlList();
		ConnectorManager cm = ConnectorManager.getInstance();
		result.setResultList(cm.getAvailableConnectors());
		return result;
	}*/
	
	@GET @Produces("application/json")
	public SCConnectors getAvailableConnecttors() {
		//ArrayList<String> rl = new ArrayList<String>();
		SCConnectors rs = new SCConnectors();
		rs.setConnectors(ConnectorManager.getInstance().getAvailableConnectorsList());
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
		return result.toString();
	}
}
