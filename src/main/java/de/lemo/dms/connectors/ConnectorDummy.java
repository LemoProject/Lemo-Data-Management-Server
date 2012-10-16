package de.lemo.dms.connectors;

import org.apache.log4j.Logger;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;

/**
 * dummy connector with sleep function for connector tests
 * @author Boris Wenzlaff
 *
 */
public class ConnectorDummy implements IConnector {
	private final int SLEEP = (60*1000);
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean testConnections() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getData(String platformName) {
		try {
			logger.info("connector dummy will load whole database");
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {

			logger.warn("connector dummy throws exception at getData()");
		}
	}

	@Override
	public void updateData(String platformName, long fromTimestamp) {
		try {
			logger.info("connector dummy will update whole database");
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
			logger.warn("connector dummy throws exception at updateData()");
		}
	}
}
