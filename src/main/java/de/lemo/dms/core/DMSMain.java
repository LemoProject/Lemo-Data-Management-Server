/**
 * File ./src/main/java/de/lemo/dms/core/DMSMain.java
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
 * File ./main/java/de/lemo/dms/core/DMSMain.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Main entry point for running the server as Java application, i.e. not in a
 * servlet container like tomcat.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @see DMSRemoteServer
 */
public final class DMSMain {

	private DMSMain() {
	}

	/**
	 * Main entry point for running the server as Java application, i.e. not in a
	 * servlet container like tomcat.
	 * 
	 * @author Boris Wenzlaff
	 * @author Leonard Kappe
	 * @param args
	 */
	public static void main(final String[] args) {
		final Logger logger = Logger.getLogger(DMSMain.class);

		try {
			DMSRemoteServer.INSTANCE.start();
			System.in.read();

		} catch (final IOException e) {
			logger.error("DMS start failed.", e);
			System.exit(1);
		}
	}
}
