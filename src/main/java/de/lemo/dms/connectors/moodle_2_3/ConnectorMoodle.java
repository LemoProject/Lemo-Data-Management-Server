package de.lemo.dms.connectors.moodle_2_3;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

public class ConnectorMoodle implements IConnector{

	private static DBConfigObject sourceDBConf;
 
	
	@Override
	public boolean testConnections() {
		try{
            Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
            session.close();
       
            IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
            dbHandler.closeSession(dbHandler.getMiningSession());
		}catch(HibernateException he)
		{
			return false;
		}
		return true;
	}

	@Override
	public void getData(String platformName) {
		
	}

	@Override
	public void updateData(String platformName, long fromTimestamp) {
		
	}

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		sourceDBConf = dbConf;
	}
}
