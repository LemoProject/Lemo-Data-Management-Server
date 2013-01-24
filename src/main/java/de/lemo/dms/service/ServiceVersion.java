/**
 * File ./main/java/de/lemo/dms/service/ServiceVersion.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.lemo.dms.core.Version;

/**
 * service for dms and db version
 * 
 * @author Boris Wenzlaff
 */
@Produces(MediaType.APPLICATION_JSON)
public class ServiceVersion extends BaseService {

	@GET
	@Path("dmsversion")
	public String getDMSVersion() {
		final Version v = new Version();
		return v.getServerVersion();
	}

	@GET
	@Path("dbversion")
	public String getDBVersion() {
		final Version v = new Version();
		return v.getDBVersion();
	}
}