package de.lemo.dms.connectors.clix2010;

import org.hibernate.HibernateException;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.db.DBConfigObject;

public class ConnectorClix implements IConnector{

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testConnections() {
		try{
			
			//ToDo - TestImpl
		}catch(HibernateException he)
		{
			he.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void getData(String platformName) {
		try{
			ClixImporter.getClixData(platformName);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateData(String platformName, long fromTimestamp) {
		try{
			ClixImporter.updateClixData(platformName, fromTimestamp);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
