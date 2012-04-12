package de.lemo.dms.service;

import de.lemo.dms.core.IServerConfiguration;
import de.lemo.dms.core.ServerConfigurationHardCoded;

/**
 * Baseclass for the service class implementation
 * @author Boris Wenzlaff
 *
 */
public abstract class ServiceBaseService {
	protected IServerConfiguration config = null;
	
	public ServiceBaseService() {
		config = ServerConfigurationHardCoded.getInstance();
	}
}
