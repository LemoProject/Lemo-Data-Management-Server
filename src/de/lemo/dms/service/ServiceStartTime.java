package de.lemo.dms.service;

import java.text.SimpleDateFormat;
import javax.ws.rs.*;

import de.lemo.dms.core.IServerConfiguration;
import de.lemo.dms.core.ServerConfigurationHardCoded;

/**
 * REST Webservice für die Startzeit
 * @author Boris Wenzlaff
 *
 */
@Path("/starttime")
public class ServiceStartTime {
	private IServerConfiguration config = null;
		
	public ServiceStartTime() {
		config = ServerConfigurationHardCoded.getInstance();
	}
	
	@GET @Produces("text/plain")
	public String startTime() {
		String result = String.valueOf(config.getStartTime());
		return result;
	}
	
	@GET @Produces("text/html")
	public String startTimeHtml() {
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss z");
		String fdd = sd.format(config.getStartTime()).toString();
		return "<html><title>Start Time</title><body><h2>Der Server wurde um "+ fdd + " gestartet.</h2></body></html>";
	}
}
