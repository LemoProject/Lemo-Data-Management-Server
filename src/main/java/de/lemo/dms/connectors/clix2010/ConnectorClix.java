/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/ConnectorClix.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/ConnectorClix.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.db.DBConfigObject;

/**
 * Connector implementation for Clix platforms
 * 
 * @author s.schwarzrock
 */
public class ConnectorClix extends AbstractConnector {

	private final DBConfigObject config;
	private Logger logger = Logger.getLogger(this.getClass());

	public ConnectorClix(final DBConfigObject config) {
		this.config = config;
	}

	@Override
	public boolean testConnections() {
		try {
			//TODO - TestImpl
		} catch (final HibernateException he)
		{
			logger.error(he.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Retrieves all data from the platform and saves it to the database.
	 */
	@Override
	public void getData() {
		final ClixImporter ci = new ClixImporter(this);
		ci.getClixData(this.config, this.getCourseIdFilter());
	}

	/**
	 * Retrieves data from the platform that is newer then the given time
	 */
	@Override
	public void updateData(final long fromTimestamp) {
		final ClixImporter ci = new ClixImporter(this);
		ci.updateClixData(this.config, fromTimestamp, this.getCourseIdFilter());
	}

}
