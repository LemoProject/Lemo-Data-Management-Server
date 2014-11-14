/**
 * File ./src/main/java/de/lemo/dms/service/ServiceStartTime.java
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
 * File ./main/java/de/lemo/dms/service/ServiceStartTime.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.text.SimpleDateFormat;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.service.servicecontainer.SCTime;

/**
 * REST Webservice for the start time of the server
 * 
 * @author Boris Wenzlaff
 */
@Path("/starttime")
public class ServiceStartTime {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SCTime startTimeJson() {
		logger.debug("call for service: startTimeJson");
		final SCTime rs = new SCTime();
		rs.setTime(ServerConfiguration.getInstance().getStartTime());
		return rs;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String startTimeHtml() {
		logger.debug("call for service: startTimeHtml");
		final SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
		final String fdd = sd.format(ServerConfiguration.getInstance().getStartTime()).toString();
		return "<html><title>Start Time</title><body><h2>The server was started on " + fdd + "</h2></body></html>";
	}
}
