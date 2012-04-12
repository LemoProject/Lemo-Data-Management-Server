package de.lemo.dms.service;

import java.text.SimpleDateFormat;
import javax.ws.rs.*;

/**
 * REST Webservice for the start time of the server
 * @author Boris Wenzlaff
 *
 */
@Path("/starttime")
public class ServiceStartTime extends ServiceBaseService{

	@GET @Produces("text/plain")
	public String startTime() {
		String result = String.valueOf(super.config.getStartTime());
		return result;
	}
	
	@GET @Produces("text/html")
	public String startTimeHtml() {
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
		String fdd = sd.format(super.config.getStartTime()).toString();
		return "<html><title>Start Time</title><body><h2>The server was started on "+ fdd + "</h2></body></html>";
	}
}
