package de.lemo.dms.connectors.moodle;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

public class ConnectorMoodle extends AbstractConnector {

    DBConfigObject config;

    public ConnectorMoodle(DBConfigObject config) {
        this.config = config;
    }

    @Override
    public boolean testConnections() {
        try {
            Session session = MoodleHibernateUtil.getSessionFactory(config).openSession();
            session.close();

            IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
            dbHandler.closeSession(dbHandler.getMiningSession());
        } catch (HibernateException he)
        {
            return false;
        }
        return true;
    }

    @Override
    public void getData() {
        ExtractAndMapMoodle extract = new ExtractAndMapMoodle(this);
        String[] s = new String[1];
        s[0] = "ExtractAndMapMoodle";
        extract.start(s, config);
    }

    @Override
    public void updateData(long fromTimestamp) {
        ExtractAndMapMoodle extract = new ExtractAndMapMoodle(this);
        String[] s = new String[2];
        s[0] = "ExtractAndMapMoodle";
        s[1] = fromTimestamp + "";
        extract.start(s, config);

    }

}
