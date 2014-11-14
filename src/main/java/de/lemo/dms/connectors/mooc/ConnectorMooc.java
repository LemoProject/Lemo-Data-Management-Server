/**
 * File ./src/main/java/de/lemo/dms/connectors/iversity/ConnectorMoodle.java
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
 * File ./main/java/de/lemo/dms/connectors/iversity/ConnectorMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.mooc;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

/**
 * Connector implementation for Iversity platforms
 * 
 * @author s.schwarzrock
 */
public class ConnectorMooc extends AbstractConnector {

	private DBConfigObject config;
	private final Logger logger = Logger.getLogger(this.getClass());

	public ConnectorMooc(final DBConfigObject config) {
		this.config = config;
	}

	@Override
	public boolean testConnections() {
		try {
			final Session session = HibernateUtil.getSessionFactory(this.config).openSession();
			session.close();

			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			dbHandler.closeSession(dbHandler.getMiningSession());
		} catch (final HibernateException he)
		{
			logger.error(he.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public void getData() {
		final ExtractAndMapMooc extract = new ExtractAndMapMooc(this);
		final String[] s = new String[1];
		s[0] = "ExtractAndMapMooc";

		extract.start(s, this.config, this.getCourseIdFilter(), this.getCourseLoginFilter());
	}

	@Override
	public void updateData(final long fromTimestamp) {
		final ExtractAndMapMooc extract = new ExtractAndMapMooc(this);
		final String[] s = new String[2];
		s[0] = "ExtractAndMapMooc";
		s[1] = fromTimestamp + "";
		extract.start(s, this.config, this.getCourseIdFilter(), this.getCourseLoginFilter());

	}

}
