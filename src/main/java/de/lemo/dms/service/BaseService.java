package de.lemo.dms.service;

import org.apache.log4j.Logger;

import de.lemo.dms.core.IServerConfiguration;
import de.lemo.dms.core.ServerConfigurationHardCoded;

/**
 * Baseclass for the service class implementation
 * 
 * @author Boris Wenzlaff
 * 
 */
public abstract class BaseService {

    protected final IServerConfiguration config = ServerConfigurationHardCoded.getInstance();
    protected final Logger logger = Logger.getLogger(this.getClass());

}
