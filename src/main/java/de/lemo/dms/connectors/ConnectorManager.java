package de.lemo.dms.connectors;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

import de.lemo.dms.core.ConnectorGetDataWorkerThread;

public enum ConnectorManager {

    INSTANCE;

    private Logger logger = Logger.getLogger(getClass());
    private List<IConnector> connectors = Lists.newArrayList();
    private ConnectorGetDataWorkerThread getDataThread;;

    /**
     * return the instance of the manager
     * 
     * @return a singleton instance of the ConnectorManager
     */
    public static ConnectorManager getInstance() {
        return INSTANCE;
    }

    /**
     * 
     * @return all available connectors
     */
    public List<IConnector> getAvailableConnectors() {
        return connectors;
    }

    public boolean addConnector(IConnector connector) {
        return connectors.add(connector);
    }

    /**
     * update the database, all data will be load
     * 
     * @return true is an connector selected otherwise false
     */
    public boolean startUpdateData(IConnector connector) {
        logger.info("Updating " + connector);
        if(connectorState() == EConnectorState.ready) {
            getDataThread = new ConnectorGetDataWorkerThread(connector);
            getDataThread.start();
            return true;
        }
        return false;
    }

    /**
     * 
     * @return the state of the connector ready = ready to load data progress = load data is in progress noconnector = no
     *         connector is selected noconfiguration = something is wrong with the configuration
     */
    public EConnectorState connectorState() {
        if(connectors.isEmpty()) {
            return EConnectorState.noconnector;
        }
        if(getDataThread.isAlive()) {
            return EConnectorState.progress;
        }
        return EConnectorState.ready;
    }

    public IConnector getConnectorById(Integer connectorId) {
        for(IConnector connector : connectors) {
            /* XXX add id*/
            // if(connector.getId() == connectorId) {
            // return connector;
            // }
        }
        return null;
    }

}
