/**
 * File ./main/java/de/lemo/dms/connectors/moodle/ConnectorMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

/**
 * Connector implementation for Moodle platforms
 * 
 * @author s.schwarzrock
 */
public class ConnectorMoodle extends AbstractConnector {

	private DBConfigObject config;

	public ConnectorMoodle(final DBConfigObject config) {
		this.config = config;
	}

	@Override
	public boolean testConnections() {
		try {
			final Session session = MoodleHibernateUtil.getSessionFactory(this.config).openSession();
			session.close();

			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			dbHandler.closeSession(dbHandler.getMiningSession());
		} catch (final HibernateException he)
		{
			return false;
		}
		return true;
	}

	@Override
	public void getData() {
		final ExtractAndMapMoodle extract = new ExtractAndMapMoodle(this);
		final String[] s = new String[1];
		s[0] = "ExtractAndMapMoodle";
		extract.start(s, this.config, this.getCourseIdFilter() );
	}

	@Override
	public void updateData(final long fromTimestamp) {
		final ExtractAndMapMoodle extract = new ExtractAndMapMoodle(this);
		final String[] s = new String[2];
		s[0] = "ExtractAndMapMoodle";
		s[1] = fromTimestamp + "";
		extract.start(s, this.config, this.getCourseIdFilter());

	}

}
