package de.lemo.dms.connectors.clix2010;

import org.hibernate.HibernateException;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.db.DBConfigObject;

/**
 * Implementation of the IConnector for Clix platforms
 * 
 * @author s.schwarzrock
 *
 */
public class ConnectorClix implements IConnector{

	/**
	 * 
	 * Use for configuration of the database connection.
	 */
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

	/**
	 * Retrieves all data from the platform and saves it to the database.
	 */
	@Override
	public void getData(String platformName) {
		try{
			ClixImporter.getClixData(platformName);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Retrieves data from the platform that is newer then the given time 
	 */
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
