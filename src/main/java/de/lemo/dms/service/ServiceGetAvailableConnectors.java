/**
 * File ./main/java/de/lemo/dms/service/ServiceGetAvailableConnectors.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.common.collect.Lists;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.service.servicecontainer.SCConnectors;

/**
 * REST web service for the available connectors of the DMS
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */

@Path("/getavailableconnectors")
public class ServiceGetAvailableConnectors extends BaseService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SCConnectors getAvailableConnecttorsJson() {
		super.logger.info("call for service: getAvailableConnecttorsJson");
		final SCConnectors rs = new SCConnectors();
		rs.setConnectors(this.getConnectors());
		return rs;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAvailableConnecttorsHtml() {
		super.logger.info("call for service: getAvailableConnecttorsHtml");
		final StringBuilder result = new StringBuilder();
		result.append("<html><title>Available Connectors</title><body><h2>Available Connectors</h2><ul>");
		for (final String s : this.getConnectors()) {
			result.append("<li>").append(s).append("</li>");
		}
		result.append("</ul></body></html>");
		return result.toString();
	}

	private List<String> getConnectors() {
		final List<String> result = Lists.newArrayList();
		for (final IConnector connector : ConnectorManager.getInstance().getAvailableConnectors()) {
			result.add(connector.toString());
		}
		return result;

	}
}
