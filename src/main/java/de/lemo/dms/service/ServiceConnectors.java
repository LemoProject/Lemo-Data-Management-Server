/**
 * File ./main/java/de/lemo/dms/service/ServiceGetAvailableConnectors.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import com.google.common.collect.Lists;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.service.servicecontainer.SCConnector;
import de.lemo.dms.service.servicecontainer.SCConnectors;

/**
 * REST web service to list available platform connectors
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
@Path("/connectors")
public class ServiceConnectors {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SCConnectors getAvailableConnecttorsJson() {
		final SCConnectors rs = new SCConnectors();
		rs.setConnectors(this.getConnectors());
		return rs;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAvailableConnecttorsHtml() {
		final StringBuilder result = new StringBuilder();
		result.append("<html><title>Available Connectors</title><body><h2>Available Connectors</h2><ul>");
		for (final SCConnector s : this.getConnectors()) {
			result.append("<li>").append(s.toString()).append("</li>");
		}
		result.append("</ul></body></html>");
		return result.toString();
	}

	private List<SCConnector> getConnectors() {
		final List<SCConnector> result = Lists.newArrayList();
		for (final IConnector connector : ConnectorManager.getInstance().getAvailableConnectors()) {
			result.add(new SCConnector(connector));
		}
		return result;
	}
}
