package de.lemo.dms.service;

import java.text.SimpleDateFormat;
import javax.ws.rs.*;

import de.lemo.dms.service.servicecontainer.SCTime;

/**
 * REST Webservice for the start time of the server
 * @author Boris Wenzlaff
 *
 */
@Path("/starttime")
public class ServiceStartTime extends ServiceBaseService{
	
	@GET
	@Produces("application/json")
	public SCTime startTimeJson() {
		SCTime rs = new SCTime();
		rs.setTime(super.config.getStartTime());
		super.logger.info("call for service: startTimeJson");
		return rs;
	}
	
	@GET @Produces("text/html")
	public String startTimeHtml() {
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
		String fdd = sd.format(super.config.getStartTime()).toString();
		super.logger.info("call for service: startTimeHtml");
		return "<html><title>Start Time</title><body><h2>The server was started on "+ fdd + "</h2></body></html>";
	}
}
