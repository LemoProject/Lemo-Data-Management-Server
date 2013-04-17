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