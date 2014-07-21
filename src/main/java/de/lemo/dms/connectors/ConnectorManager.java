/**
 * File ./src/main/java/de/lemo/dms/connectors/ConnectorManager.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/ConnectorManager.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

import de.lemo.dms.core.ConnectorGetDataWorkerThread;
import de.lemo.dms.db.DBConfigObject;
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
	
	public IConnector createNewConnector(Long platformId, Long prefix, String name, String platformType, List<String> properties, List<Long> courseIdFilter, List<String> logins)
	{
		ESourcePlatform platform = ESourcePlatform.valueOf(platformType);
		DBConfigObject config = new DBConfigObject();
		if(!properties.isEmpty()){
			for(int i = 0; i < properties.size() - 1; i += 2)
			{
				config.setProperty(properties.get(i), properties.get(i + 1));
			}
		}		
		IConnector connector = platform.newConnector(platformId, name, config, courseIdFilter, logins);
		this.addConnector(connector);
		return connector;
	}

	/**
	 * Update the mining database, new data will be loaded from the platform.
	 * 
	 * @return true if updating has started
	 */
	public boolean startUpdateData(final IConnector connector) {
		this.logger.info("Updating " + connector);
		if ((this.getDataThread == null) || (this.connectorState() == EConnectorManagerState.READY)) {
			this.getDataThread = new ConnectorGetDataWorkerThread(connector);
			this.getDataThread.start();
			return true;
		}
		return false;
	}

	/**
	 * Information about the connector update process.
	 * 
	 * @see EConnectorManagerState
	 * @return the state of the connector update process
	 */
	public EConnectorManagerState connectorState() {
		if (this.connectors.isEmpty()) {
			return EConnectorManagerState.NO_CONNECTORS;
		}
		if (getDataThread != null && this.getDataThread.isAlive()) {
			return EConnectorManagerState.IN_PROGRESS;
		}
		return EConnectorManagerState.READY;
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
		/*
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		Platform platform = (Platform) session.get(Platform.class, connector.getPlatformId());

		if (platform == null) {
			// save new platform
			final Criteria criteria = session
					.createCriteria(Platform.class)
					.setProjection(Projections.max("prefix"));
			Long maxPrefix = (Long) criteria.uniqueResult();
			if (maxPrefix == null) {
				maxPrefix = 10L;
			}
			platform = new Platform();
			platform.setId(connector.getPlatformId());
			platform.setPrefix(maxPrefix + 1);

		}
		final AbstractConnector ac = (AbstractConnector) connector;
		ac.setPrefix(platform.getPrefix());
		// update name
		platform.setTitle(connector.getName());
		platform.setType(connector.getPlattformType().name());

		dbHandler.saveToDB(session, platform);
		dbHandler.closeSession(session);
		*/
	}

}
