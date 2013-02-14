/**
 * File ./main/java/de/lemo/dms/service/responses/ResourceNotFoundException.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
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
	 * @param message
	 *            the String that is the entity of the 404 response.
	 */
	public ResourceNotFoundException(final String message) {
		super(Response.status(Status.NOT_FOUND).entity(message).type("text/plain").build());
	}

}