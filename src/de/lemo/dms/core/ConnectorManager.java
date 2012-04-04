package de.lemo.dms.core;

import java.util.HashMap;
import java.util.Set;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IConnector;

public class ConnectorManager {
	private static ConnectorManager instance = null;
	private HashMap<String, IConnector> connectors;
	private IConnector selectedConnector;
	
	//constructor with singleton pattern
	private ConnectorManager() {
		selectedConnector = null;
		//init the Connectors
		connectors = new HashMap<String, IConnector>();
		//add the connectors
		connectors.put("moodle", null);
		connectors.put("clix", null);
		connectors.put("fiz", null);
	}
	
	/**
	 * return the instance of the manager
	 * @return a singleton instance of the ConnectorManager
	 */
	public static ConnectorManager getInstance() {
		if(instance == null) {
			instance = new ConnectorManager();
		}
		return instance;
	}
	
	/**
	 * 
	 * @return a set with the names of the available connectors
	 */
	public Set<String> getAvailableConnectors() {
		return connectors.keySet();
	}
	
	/**
	 * select one of the connectors
	 * @param connectorName
	 * @return true is the parameter an correct selection otherwise false
	 */
	public boolean selectConnector(String connectorName) {
		if(connectors.containsKey(connectorName)) {
			selectedConnector = connectors.get(connectorName);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * update the database from an specific date
	 * @param fromTimestamp
	 * @return true is an connector selected otherwise false
	 */
	public boolean updateData(long fromTimestamp) {
		if(selectedConnector == null) {
			return false;
		}
		selectedConnector.updateData(fromTimestamp);
		return true;
	}
	
	/**
	 * update the database, all data will be load
	 * @return true is an connector selected otherwise false
	 */
	public boolean getData() {
		if(selectedConnector == null) {
			return false;
		}
		selectedConnector.getData();
		return true;
	}
	
	/**
	 * set the configuration for the source db
	 * @param dbConf
	 * @return true is an connector selected otherwise false
	 */
	public boolean setSourceDBConfig(DBConfigObject dbConf) {
		//TODO schreiben der Konfiguration in eine config datei
		if(selectedConnector == null) {
			return false;
		}
		selectedConnector.setSourceDBConfig(dbConf);
		return true;
	}
	
	/**
	 * set the configuration for the destination db
	 * @param dbConf
	 * @return true is an connector selected otherwise false
	 */
	public boolean setMiningDBConfig(DBConfigObject dbConf) {
		//TODO schreiben der Konfiguration in eine config datei
		if(selectedConnector == null) {
			return false;
		}
		selectedConnector.setMiningDBConfig(dbConf);
		return true;
	}
	
	/**
	 * 
	 * @return true is the connection ok, otherwise false
	 */
	public boolean testConnections() {
		return selectedConnector.testConnections();
	}
	
	/**
	 * load a available configuration for the db from a config file
	 * @return true ist a configuration available, otherwise false
	 */
	public boolean loadDefaultConfiguration() {
		//TODO implementieren des speicherns und ladens aus konfigurationsdateien
		return false;
	}
}
