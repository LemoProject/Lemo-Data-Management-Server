package de.lemo.dms.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import de.lemo.dms.connectors.ConnectorDummy;
import de.lemo.dms.connectors.EConnectorState;
import de.lemo.dms.connectors.ESourcePlatform;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.moodle.ConnectorMoodle;
import de.lemo.dms.db.DBConfigObject;

public class ConnectorManager {
	private static ConnectorManager instance = null;
	private IServerConfiguration config = ServerConfigurationHardCoded.getInstance();
	private HashMap<ESourcePlatform, IConnector> connectors;
	private IConnector selectedConnector;
	private Logger logger = config.getLogger();
	private ConnectorGetDataWorkerThread getDataThread = null;
	
	//constructor with singleton pattern
	private ConnectorManager() {
		selectedConnector = null;
		getDataThread = new ConnectorGetDataWorkerThread(selectedConnector);
		//init the Connectors
		connectors = new HashMap<ESourcePlatform, IConnector>();
		//add the connectors
		connectors.put(ESourcePlatform.Dummy, new ConnectorDummy());
		connectors.put(ESourcePlatform.Moodle_1_9, new ConnectorMoodle());
		connectors.put(ESourcePlatform.Moodle_1_9_Numeric, new de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle());
		connectors.put(ESourcePlatform.Chemgaroo, new ConnectorChemgapedia());
		
		//TODO Manuelles setzen der DB Konfig
		//TODO muss ausgelagert werden
		connectors.get(ESourcePlatform.Moodle_1_9).setSourceDBConfig(ServerConfigurationHardCoded.getInstance().getSourceDBConfig());
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
	public Set<String> getAvailableConnectorsSet() {
		Set<String> result = new HashSet<String>();
		for(ESourcePlatform key : connectors.keySet()) {
			result.add(key.name());
		}
		return result;
	}
	
	/**
	 * 
	 * @return a list of strings with the names of the available connectors
	 */
	public List<String> getAvailableConnectorsList() {
		ArrayList<String> rs = new ArrayList<String>(getAvailableConnectorsSet());
		return rs;
	}
	
	/**
	 * select one of the connectors
	 * @param connectorName
	 * @return true is the parameter an correct selection otherwise false
	 */
	public boolean selectConnector(ESourcePlatform connectorName) {
		if(connectors.containsKey(connectorName)) {
			selectedConnector = connectors.get(connectorName);
			logger.info("selected connector in dms: "+connectorName.name());
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
		//no connector
		if(selectedConnector == null) {
			return false;
		}
		//connector is loading data
		if(!getDataThread.isAlive()) {
			getDataThread = new ConnectorGetDataWorkerThread(selectedConnector);
			getDataThread.start();
			return true;
		}
		else {
			return false;
		}
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
	
	/**
	 * 
	 * @return the state of the connector
	 * ready = ready to load data
	 * progress = load data is in progress
	 * noconnector = no connector is selected
	 * noconfiguration = something is wrong with the configuration 
	 */
	public EConnectorState connectorState() {
		if(selectedConnector == null) {
			return EConnectorState.noconnector;
		}
		if(testConnections() == false) {
			return EConnectorState.noconfiguration;
		}
		if(getDataThread.isAlive()) {
			return EConnectorState.progress;
		}
		return EConnectorState.ready;
	}
}
