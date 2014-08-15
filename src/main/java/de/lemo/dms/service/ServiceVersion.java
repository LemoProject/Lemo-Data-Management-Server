/**
 * File ./src/main/java/de/lemo/dms/service/ServiceVersion.java
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
 * File ./main/java/de/lemo/dms/service/ServiceVersion.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
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
@Path("version")
public class ServiceVersion {

	@GET
	@Path("dms")
	public String getDMSVersion() {
		final Version v = new Version();
		return v.getServerVersion();
	}

	@GET
	@Path("db")
	public String getDBVersion() {
		final Version v = new Version();
		return v.getDBVersion();
	}
}