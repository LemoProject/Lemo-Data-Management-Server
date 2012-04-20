package de.lemo.dms.connectors.moodleNumericId;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.HibernateDBHandler;
import de.lemo.dms.connectors.IConnector;

public class ConnectorMoodle implements IConnector{

	static DBConfigObject sourceDBConf;
	static DBConfigObject miningDBConf;
	static IDBHandler dbHandler;
	
	@Override
	public boolean testConnections() {
		try{
	        Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle(sourceDBConf).openSession();
	        session.close();
	        HibernateDBHandler target= new HibernateDBHandler();
	        target.getConnection(miningDBConf);
	        target.closeConnection();
		}catch(HibernateException he)
		{
			return false;
		}
		return true;
	}

	@Override
	public void getData() {
		ExtractAndMapMoodle extract = new ExtractAndMapMoodle();	
		String[] s = new String[1];
		s[0] = "ExtractAndMapMoodle";
		extract.start(s, sourceDBConf);		
	}

	@Override
	public void updateData(long fromTimestamp) {
		ExtractAndMapMoodle extract = new ExtractAndMapMoodle();	
		String[] s = new String[2];
		s[0] = "ExtractAndMapMoodle";
		s[1] = fromTimestamp+"";
		extract.start(s, sourceDBConf);
		
	}

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		sourceDBConf = dbConf;
		
	}
}
