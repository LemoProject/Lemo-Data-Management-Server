/**
 * File ./src/main/java/de/lemo/dms/connectors/ConnectorDummy.java
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
 * File ./main/java/de/lemo/dms/connectors/ConnectorDummy.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import org.apache.log4j.Logger;

/**
 * dummy connector with sleep function for connector tests
 * 
 * @author Boris Wenzlaff
 */
public class ConnectorDummy extends AbstractConnector {

	private final int sleep = (60 * 1000);
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean testConnections() {
		return true;
	}

	@Override
	public void getData() {
		try {
			this.logger.info("connector dummy will load whole database");
			Thread.sleep(this.sleep);
		} catch (final InterruptedException e) {

			this.logger.warn("connector dummy throws exception at getData()");
		}
	}

	@Override
	public void updateData(final long fromTimestamp) {
		try {
			this.logger.info("connector dummy will update whole database");
			Thread.sleep(this.sleep);
		} catch (final InterruptedException e) {
			this.logger.warn("connector dummy throws exception at updateData()");
		}
	}
}
