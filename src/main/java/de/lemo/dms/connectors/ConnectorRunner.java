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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.lemo.dms.core.config.ServerConfiguration;



/**
 * Just a class for Connector-related tests.
 * 
 * @author s.schwarzrock
 */
public class ConnectorRunner {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	public void runConnectors(List<String> connectorNames )
	{
		
		ServerConfiguration.getInstance().loadConfig("/lemo");
		List<IConnector> conns = ConnectorManager.getInstance().getAvailableConnectors();

		for(IConnector c : conns)
		{
			if((connectorNames.isEmpty() || connectorNames.contains(c.getName())) && c.testConnections())
			{	
				this.logger.info("Starting Import for connector " + c.getName());
				c.getData();
				this.logger.info("Import finished for connector " + c.getName());
			}
		}
	}

	public static void main(final String[] args)
	{
		List<String> names = new ArrayList<String>();
		if(args != null && args.length > 0)
			for(String s : args)
				names.add(s);
		final ConnectorRunner t = new ConnectorRunner();
		t.runConnectors(names);
	}

}
