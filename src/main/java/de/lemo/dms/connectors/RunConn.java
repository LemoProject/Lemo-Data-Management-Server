/**
 * File ./src/main/java/de/lemo/dms/connectors/RunConn.java
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
 * File ./main/java/de/lemo/dms/connectors/RunConn.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;



/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 */
public class RunConn {

	private static final Long ID_MOODLE23 = 4L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public void run()
	{
		this.logger.info("Starting Import");
		ServerConfiguration.getInstance().loadConfig("/lemo");
		final IConnector connector = ConnectorManager.getInstance().getConnectorById(RunConn.ID_MOODLE23);
		connector.getData();
		this.logger.info("Import finished");
	}

	/**
	 * main method for test case 
	 * TODO move to unit test
	 * @param args
	 */
	public static void main(final String[] args)
	{

		final RunConn t = new RunConn();
		t.run();
	}

}
