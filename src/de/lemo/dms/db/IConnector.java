package de.lemo.dms.db;


import de.lemo.dms.db.DBConfigObject;

/**
 * The Interface IConnector.
 */
public interface IConnector {
	
	/**
	 * Connfiguration for the source DB
	 * @param dbConf
	 */
	public void setSourceDBConfig(DBConfigObject dbConf);
	
	/**
	 * Connfiguration for the destination DB
	 * @param dbConf
	 */
	public void setMiningDBConfig(DBConfigObject dbConf);
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