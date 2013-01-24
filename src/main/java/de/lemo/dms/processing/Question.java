/**
 * File ./main/java/de/lemo/dms/processing/Question.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.lemo.dms.service.BaseService;

/**
 * Base class for questions, sets default JSON response type.
 * 
 * @author Leonard Kappe
 */
@Produces(MediaType.APPLICATION_JSON)
public abstract class Question extends BaseService {

}
