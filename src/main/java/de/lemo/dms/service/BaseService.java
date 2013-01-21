package de.lemo.dms.service;

import org.apache.log4j.Logger;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;

/**
 * Baseclass for the service class implementation
 * 
 * @author Boris Wenzlaff
 * 
 */
public abstract class BaseService {

    protected final ServerConfiguration config = ServerConfiguration.getInstance();
    protected final Logger logger = Logger.getLogger(this.getClass());
    IDBHandler dbHandler = config.getMiningDbHandler();

}
