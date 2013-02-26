/**
 * File ./main/java/de/lemo/dms/connectors/chemgapedia/ConnectorChemgapedia.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.chemgapedia;

import java.io.File;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import de.lemo.dms.connectors.AbstractConnector;
import de.lemo.dms.connectors.chemgapedia.fizHelper.LogReader;
import de.lemo.dms.connectors.chemgapedia.fizHelper.XMLPackageParser;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ConfigMining;

/**
 * Connector implementation for the platform Chemgapedia
 * 
 * @author s.schwarzrock
 */
public class ConnectorChemgapedia extends AbstractConnector {

	private static final String PATH_LOG_FILE = "lemo.log_file_path";
	private static final String PATH_RESOURCE_METADATA = "lemo.resource_metadata_path";
	private static final String PROCESS_LOG_FILE = "lemo.process_log_file";
	private static final String PROCESS_METADATA = "lemo.process_metadata";
	private static final String FILTER_LOG_FILE = "lemo.filter_log_file";

	private final boolean filter;
	private final boolean processVSC;
	private final boolean processLog;
	private final String logPath;
	private final String vscPath;
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final int THOU = 1000;

	public ConnectorChemgapedia(final DBConfigObject config) {
		final Map<String, String> props = config.getProperties();

		// required
		this.logPath = props.get(ConnectorChemgapedia.PATH_LOG_FILE);
		if (props.get(ConnectorChemgapedia.PATH_LOG_FILE) == null) {
			this.logger.error("Connector Chemgapedia : No path for log file defined");
		}

		// optional
		this.filter = props.containsKey(ConnectorChemgapedia.FILTER_LOG_FILE)
				&& props.get(ConnectorChemgapedia.FILTER_LOG_FILE).toLowerCase().equals("true");
		this.processVSC = props.containsKey(ConnectorChemgapedia.PROCESS_METADATA)
				&& props.get(ConnectorChemgapedia.PROCESS_METADATA).toLowerCase().equals("true");
		this.processLog = props.containsKey(ConnectorChemgapedia.PROCESS_LOG_FILE)
				&& props.get(ConnectorChemgapedia.PROCESS_LOG_FILE).toLowerCase().equals("true");

		// conditionally required
		this.vscPath = props.get(ConnectorChemgapedia.PATH_RESOURCE_METADATA);
		if ((this.vscPath == null) && this.processVSC) {
			this.logger.error("Connector Chemgapedia : No path for resource metadata defined");
		}

	}

	@Override
	public boolean testConnections() {

		if (this.logPath == null)
		{
			this.logger.error("Connector Chemgapedia : No path for log file defined");
			return false;
		}
		if (this.vscPath == null)
		{
			this.logger.error("Connector Chemgapedia : No path for resource metadata defined");
			return false;
		}
		final File f = new File(this.logPath);
		if (!f.exists())
		{
			this.logger.error("Connector Chemgapedia : Defined Log file doesn't exist.");
			return false;
		}

		return true;
	}

	@Override
	public void getData() {
		final Long starttime = System.currentTimeMillis() / THOU;

		if (this.processVSC || this.processLog)
		{
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			Session session = dbHandler.getMiningSession();

			if (this.processVSC)
			{
				final XMLPackageParser x = new XMLPackageParser(this);
				x.readAllVlus(this.vscPath);
				x.saveAllToDB();
				x.clearMaps();
			}
			if (this.processLog)
			{
				final LogReader logR = new LogReader(this, session);
				logR.loadServerLogData(this.logPath, 0L, this.filter, session);
				logR.save(session);
				logR.clearMaps();
			}

			final Long endtime = System.currentTimeMillis() / THOU;
			final ConfigMining config = new ConfigMining();
			config.setLastModifiedLong(System.currentTimeMillis());
			config.setElapsedTime((endtime) - (starttime));
			config.setDatabaseModel("1.2");
			config.setPlatform(this.getPlatformId());

			session = dbHandler.getMiningSession();
			dbHandler.saveToDB(session, config);
			dbHandler.closeSession(session);
		}
	}

	@Override
	public void updateData(final long fromTimestamp) {
		final Long starttime = System.currentTimeMillis() / THOU;

		if (this.processVSC || this.processLog)
		{
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();

			if (this.processVSC)
			{
				final XMLPackageParser x = new XMLPackageParser(this);
				x.readAllVlus(this.vscPath);
				x.saveAllToDB();
				x.clearMaps();
			}
			if (this.processLog)
			{
				final LogReader logR = new LogReader(this, session);
				logR.loadServerLogData(this.logPath, 0L, this.filter, session);
				logR.save(session);
				logR.clearMaps();
			}

			final Long endtime = System.currentTimeMillis() / THOU;
			final ConfigMining config = new ConfigMining();
			config.setLastModifiedLong(System.currentTimeMillis());
			config.setElapsedTime((endtime) - (starttime));
			config.setDatabaseModel("1.2");
			config.setPlatform(this.getPlatformId());

			dbHandler.saveToDB(session, config);
			dbHandler.closeSession(session);
		}
	}

}
