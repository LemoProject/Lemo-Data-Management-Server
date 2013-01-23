package de.lemo.dms.processing;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.service.BaseService;

/**
 * Base class for questions, sets default JSON response type.
 * 
 * @author Leonard Kappe
 */

@Produces(MediaType.APPLICATION_JSON)
public abstract class Question extends BaseService {

	// TODO remove, it's already in BaseService
    protected final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

}
