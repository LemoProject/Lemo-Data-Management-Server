/**
 * File ./src/main/java/de/lemo/dms/service/responses/BadRequestException.java
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
 * File ./main/java/de/lemo/dms/service/responses/BadRequestException.java
 * Date 2013-02-26
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service.responses;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

/**
 * Exceptional response for <i>bad requests</i>, e.g. with bad parameters.
 * 
 * @author Leonard Kappe
 */
public class BadRequestException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a HTTP 400 (Bad request) exception.
	 */
	public BadRequestException() {
		super(Status.BAD_REQUEST);
	}

	/**
	 * Create a HTTP 400 (Bad request) exception with a custom message.
	 * 
	 * @param message
	 *            details about the response
	 */
	public BadRequestException(final String message) {
		super(Response.status(Status.NOT_FOUND).entity(message).type("text/plain").build());
	}

}