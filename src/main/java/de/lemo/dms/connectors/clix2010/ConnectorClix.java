package de.lemo.dms.connectors.clix2010;

import org.hibernate.HibernateException;

import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.db.DBConfigObject;

public class ConnectorClix extends AbstractConnector {

    private DBConfigObject config;

    public ConnectorClix(DBConfigObject config) {
        this.config = config;
    }

    @Override
    public boolean testConnections() {
        try {

            // ToDo - TestImpl
        } catch (HibernateException he)
        {
            he.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void getData() {
        ClixImporter ci = new ClixImporter(this);
        ci.getClixData(config);
    }

    @Override
    public void updateData(long fromTimestamp) {
        ClixImporter ci = new ClixImporter(this);
        ci.updateClixData(config, fromTimestamp);
    }

}
