/**
 * File ./src/main/java/de/lemo/dms/core/ConnectorGetDataWorkerThread.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/core/ConnectorGetDataWorkerThread.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import org.apache.log4j.Logger;
import de.lemo.dms.connectors.IConnector;

/**
 * Background worker thread for the connectors.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public class ConnectorGetDataWorkerThread extends Thread {

	private final IConnector connector;
	private final Logger logger = Logger.getLogger(this.getClass());

	public ConnectorGetDataWorkerThread(final IConnector connector) {
		this.connector = connector;
	}

	public IConnector getConnector() {
		return connector;
	}

	@Override
	public void run() {
		this.logger.info("Running connector update " + this.connector);
		this.connector.getData();
	}
}
