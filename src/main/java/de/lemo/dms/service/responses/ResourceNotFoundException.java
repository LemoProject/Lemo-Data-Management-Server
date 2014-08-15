/**
 * File ./src/main/java/de/lemo/dms/service/responses/ResourceNotFoundException.java
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
 * File ./main/java/de/lemo/dms/service/responses/ResourceNotFoundException.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service.responses;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

/**
 * Encapsulates exceptions where resources were not found
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 *
 */
public class ResourceNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 */
	public ResourceNotFoundException() {
		super(Status.NOT_FOUND);
	}

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 * 
	 * @param resourceDescription
	 *            the String that is the entity of the 404 response.
	 */
	public ResourceNotFoundException(final String resourceDescription) {
		super(Response.status(Status.NOT_FOUND).entity("Resource not found: " + resourceDescription).type(MediaType.TEXT_PLAIN).build());
	}

}