package de.lemo.dms.processing;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.service.BaseService;

/**
 * Base question, assumes default json response type.
 * 
 * @author Leonard Kappe
 */

@Produces(MediaType.APPLICATION_JSON)
public abstract class Question extends BaseService {

    protected final IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();

}
