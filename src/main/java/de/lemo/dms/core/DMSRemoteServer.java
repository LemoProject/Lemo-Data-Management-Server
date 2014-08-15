/**
 * File ./src/main/java/de/lemo/dms/core/DMSRemoteServer.java
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
 * File ./main/java/de/lemo/dms/core/DMSRemoteServer.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;

/**
 * Server instance which can be run as a Java application.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public enum DMSRemoteServer {

	INSTANCE;

	private static final int PORT = 8081;
	private static final String HOST = "localhost";
	private final Logger logger = Logger.getLogger(this.getClass());
	private HttpServer server;

	/**
	 * Stop it.
	 */
	private void stop() {
		if (this.server != null) {
			this.server.stop();
			this.logger.info("remote server stopped...");
			this.server = null;
		}
	}

	/**
	 * Stop and start the server again.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	protected void restart() throws IOException {
		this.stop();
		this.start();
	}

	/**
	 * Start and initialize the server if it isn't already running.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	protected void start() throws IOException {
		if (this.server == null) {
			this.server = GrizzlyServerFactory.createHttpServer("http://" + DMSRemoteServer.HOST + ":"
					+ DMSRemoteServer.PORT,
					new DMSResourceConfig());
			this.server.start();

			final Version v = new Version();
			this.logger.info("DMS started on " + DMSRemoteServer.HOST + ":" + DMSRemoteServer.PORT + ", Version: "
					+ v.getServerVersion());
		}
		else {
			this.logger.info("DMS already running on " + DMSRemoteServer.HOST + ":" + DMSRemoteServer.PORT);
		}
	}
}
