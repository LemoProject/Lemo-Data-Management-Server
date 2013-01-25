/**
 * File ./main/java/de/lemo/dms/service/BaseService.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;

/**
 * Base class for services, provides database handler and logger.
 * 
 * @author Boris Wenzlaff
 */
public abstract class BaseService {

	protected final ServerConfiguration config = ServerConfiguration.getInstance();
	protected final Logger logger = Logger.getLogger(this.getClass());
	protected final IDBHandler dbHandler = this.config.getMiningDbHandler();

}
