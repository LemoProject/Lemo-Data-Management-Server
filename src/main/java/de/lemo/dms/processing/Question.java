/**
 * File ./main/java/de/lemo/dms/processing/Question.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 * Base class for questions, sets default JSON response type.
 * 
 * @author Leonard Kappe
 */
@Produces(MediaType.APPLICATION_JSON)
public abstract class Question {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected void validateTimestamps(Long startTime, Long endTime) throws WebApplicationException {
		if (startTime == null || endTime == null || startTime >= endTime) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

	protected void validateTimestamps(Long startTime, Long endTime, Long resolution) throws WebApplicationException {
		validateTimestamps(startTime, endTime);
		if (resolution == null || resolution <= 0) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

}
