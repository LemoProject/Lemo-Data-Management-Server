package de.lemo.dms.connectors;


import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
// TODO: Auto-generated Javadoc
/**
 * The Interface IConnector.
 */
public interface IConnector {
	
	
	/**
	 * Sets the parameters for the source database via a DBConfigObject.
	 * @param dbConf
	 */
	public void setSourceDBConfig(DBConfigObject dbConf);
	
	/**
	 * Sets the parameters for the mining database via a DBConfigobject.
	 * @param dbConf
	 */
	public void setMiningDBConfig(DBConfigObject dbConf, IDBHandler dbHandler);
	/**
	 * Tests the configured connection.
	 *
	 * @param conf the database configuration object, holding connection settings
	 * @return true, if successful
	 */
	public boolean testConnections();
	
	/**
	 * Gets the data.
	 *
	 * @param conf the database configuration object, holding connection settings
	 * @return the data
	 */
	public void getData();
	
	/**
	 * Update data.
	 *
	 * @param conf the database configuration object, holding connection settings
	 * @param fromTimestamp the from timestamp
	 */
	public void updateData(long fromTimestamp);

}
