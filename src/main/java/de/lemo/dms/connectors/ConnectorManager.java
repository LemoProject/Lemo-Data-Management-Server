/**
 * File ./main/java/de/lemo/dms/connectors/ConnectorManager.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.ArrayList;
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

/**
 * Handles all connector instances.
 * 
 * @author Leonard Kappe
 * @author Boris Wenzlaff
 */
public enum ConnectorManager {

	INSTANCE;

	private final Logger logger = Logger.getLogger(this.getClass());
	private final List<IConnector> connectors = Lists.newArrayList();
	private ConnectorGetDataWorkerThread getDataThread;

	/**
	 * Return the instance of the manager.
	 * 
	 * @return a singleton instance of the ConnectorManager
	 */
	public static ConnectorManager getInstance() {
		return INSTANCE;
	}

	/**
	 * @return all available connectors
	 */
	public List<IConnector> getAvailableConnectors() {
		return new ArrayList<IConnector>(connectors);
	}

	/**
	 * Add a connector.
	 * 
	 * @param connector
	 */
	public void addConnector(final IConnector connector) {
		this.saveOrUpdateConnectorInfo(connector);
		this.connectors.add(connector);
	}

	/**
	 * Update the mining database, new data will be loaded from the platform.
	 * 
	 * @return true if updating has started
	 */
	public boolean startUpdateData(final IConnector connector) {
		this.logger.info("Updating " + connector);
		if ((this.getDataThread == null) || (this.connectorState() == EConnectorState.READY)) {
			this.getDataThread = new ConnectorGetDataWorkerThread(connector);
			this.getDataThread.start();
			return true;
		}
		return false;
	}

	/**
	 * Information about the connector update process.
	 * 
	 * @see EConnectorState
	 * @return the state of the connector update process
	 */
	public EConnectorState connectorState() {
		if (this.connectors.isEmpty()) {
			return EConnectorState.NO_CONNECTORS;
		}
		if (this.getDataThread.isAlive()) {
			return EConnectorState.IN_PROGRESS;
		}
		return EConnectorState.READY;
	}

	public IConnector getUpdatingConnector() {
		if (getDataThread != null) {
			return getDataThread.getConnector();
		}
		return null;
	}

	/**
	 * Get a connector by its id.
	 * 
	 * @param connectorId
	 *            a connector id
	 * @return the connector with the provided id or null if none found
	 */
	public IConnector getConnectorById(final Long connectorId) {
		if (connectorId != null) {
			for (final IConnector connector : this.connectors) {
				if (connectorId.equals(connector.getPlatformId())) {
					return connector;
				}
			}
		}
		return null;
	}

	private void saveOrUpdateConnectorInfo(final IConnector connector) {
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		PlatformMining platform = (PlatformMining) session.get(PlatformMining.class, connector.getPlatformId());

		if (platform == null) {
			// save new platform
			final Criteria criteria = session
					.createCriteria(PlatformMining.class)
					.setProjection(Projections.max("prefix"));
			Long maxPrefix = (Long) criteria.uniqueResult();
			if (maxPrefix == null) {
				maxPrefix = 10L;
			}
			platform = new PlatformMining();
			platform.setId(connector.getPlatformId());
			platform.setPrefix(maxPrefix + 1);

		}
		final AbstractConnector ac = (AbstractConnector) connector;
		ac.setPrefix(platform.getPrefix());
		// update name
		platform.setName(connector.getName());
		platform.setType(connector.getPlattformType().name());

		dbHandler.saveToDB(session, platform);
		dbHandler.closeSession(session);
	}

}
