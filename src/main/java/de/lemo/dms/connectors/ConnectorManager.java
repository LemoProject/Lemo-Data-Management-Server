package de.lemo.dms.connectors;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.google.common.collect.Lists;

import de.lemo.dms.core.ConnectorGetDataWorkerThread;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.PlatformMining;

public enum ConnectorManager {

    INSTANCE;

    private Logger logger = Logger.getLogger(getClass());
    private List<IConnector> connectors = Lists.newArrayList();
    private ConnectorGetDataWorkerThread getDataThread;

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
     * update the database, all data will be loaded
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

    public IConnector getConnectorById(Long connectorId) {
        for(IConnector connector : connectors) {
            if(connector.getPlatformId().equals(connectorId)) {
                return connector;
            }
        }
        return null;
    }

    public void saveOrUpdateConnectorInfo(IConnector connector) {
        IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();

        PlatformMining platform = (PlatformMining) session.get(PlatformMining.class, connector.getPlatformId());

        if(platform == null) {
            Criteria criteria = session
                    .createCriteria(PlatformMining.class)
                    .setProjection(Projections.max("prefix"));
            Long maxPrefix = (Long) criteria.uniqueResult();
            if(maxPrefix == null) {
                maxPrefix = 0L;
            }
            platform = new PlatformMining();
            platform.setId(connector.getPlatformId());
            platform.setPrefix(maxPrefix + 1);
        }

        platform.setName(connector.getName());
        platform.setType(connector.getPlattformType().name());

        session.save(platform);
    }
}
