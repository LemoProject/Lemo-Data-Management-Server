/**
 * File ./main/java/de/lemo/dms/processing/Question.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import de.lemo.dms.service.responses.BadRequestException;

/**
 * Base class for questions, sets default JSON response type.
 * 
 * @author Leonard Kappe
 */
@Produces(MediaType.APPLICATION_JSON)
public abstract class Question {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected void validateTimestamps(Long startTime, Long endTime) {
		if (startTime == null) {
			throw new BadRequestException("Missing start time parameter.");
		}
		if (endTime == null) {
			throw new BadRequestException("Missing end time parameter.");
		}
		if (startTime >= endTime) {
			throw new BadRequestException("Invalid start time: start time exceeds end time.");
		}
	}

	protected void validateTimestamps(Long startTime, Long endTime, Long resolution) {
		validateTimestamps(startTime, endTime);
		if (resolution == null) {
			throw new BadRequestException("Missing resolution parameter.");
		}
		if (resolution <= 0) {
			throw new BadRequestException("Invalid resolution: resolution is a negative value.");
		}
	}

}
