/**
 * File ./main/java/de/lemo/dms/core/config/ServerConfiguration.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.ESourcePlatform;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.HibernateDBHandler;
import de.lemo.dms.db.hibernate.MiningHibernateUtil;

/**
 * Class with the configuration parameter for the server
 * @author Leonard Kappe
 */
public enum ServerConfiguration {

	/**
	 * Singleton instance.
	 */
	INSTANCE;

	private static final String DEFAULT_PATTERN = "[%p] %d{ISO8601} [%c{1}] - %m%n";

	{
		// the very first place where we can initialize the logger
		Logger.getRootLogger().setLevel(Level.INFO);
		Logger.getRootLogger().addAppender(
				new ConsoleAppender(new PatternLayout(ServerConfiguration.DEFAULT_PATTERN)));
	}

	private final Logger logger = Logger.getLogger(this.getClass());
	private IDBHandler miningDbHandler;
	private long startTime;
	private String serverName;

	/**
	 * Gets the instance of the server's configuration.
	 * 
	 * @return the singleton instance
	 */
	public static ServerConfiguration getInstance() {
		return INSTANCE;
	}

	/**
	 * The context path is used to determine the configuration file's name. The
	 * first char (a slash by convention) will be
	 * ignored. Some context paths aren't valid file names (like the root
	 * context or those with sub-paths), so tomcat's
	 * naming conventions for .war files will be used.
	 * Examples:
	 * 
	 * <pre>
	 * context   -> file name
	 * -------------------------
	 * /         -> ROOT.xml
	 * /lemo     -> lemo.xml
	 * /lemo/foo -> lemo#foo.xml
	 * </pre>
	 * 
	 * @param contextPath
	 *            the application's context path (URL relative to context root)
	 */
	public void loadConfig(final String contextPath) {
		final LemoConfig lemoConfig = this.readConfigFiles(contextPath);

		this.serverName = lemoConfig.dataManagementServer.name;

		this.logger.info("Inititalizing mining database");
		final DBConfigObject miningDBConfig = this.createDBConfig(lemoConfig.dataManagementServer.databaseProperties);
		MiningHibernateUtil.initSessionFactory(miningDBConfig);
		this.miningDbHandler = new HibernateDBHandler();

		final List<IConnector> connectors = this.createConnectors(lemoConfig.dataManagementServer.connectors);
		final ConnectorManager connectorManager = ConnectorManager.getInstance();
		for (final IConnector connector : connectors) {
			connectorManager.addConnector(connector);
		}
	}

	private LemoConfig readConfigFiles(String contextPath) {
		String contextPathTMP = contextPath;
		if (contextPathTMP.isEmpty()) {
			// empty means root context, Tomcat convention for root context war files is 'ROOT.war'
			contextPathTMP = "/ROOT";
		}

		// remove leading slash, replace any slashes with hashes like Tomcat
		// does with the war files
		final String warName = contextPathTMP.substring(1).replace('/', '#');

		final Set<String> fileNames = new LinkedHashSet<String>();
		
		// default, based on war name
		fileNames.add(warName + ".xml");

		int lastHash;
		String warPath = warName;
		while ((lastHash = warPath.lastIndexOf('#')) > 0) {
			// try to read more generic name by removing sub-paths, i.e.
			// /lemo/dms -> /lemo (useful to read the appserver's config file,
			// so only one file is needed)
			warPath = warPath.substring(0, lastHash);
			fileNames.add(warPath + ".xml");
		}

		// eventually try generic lemo.xml for use in local development
		fileNames.add("lemo.xml");

		LemoConfig lemoConfig = null;
		try {
			final Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(
					LemoConfig.class).createUnmarshaller();
			for (final String fileName : fileNames) {
				final URL resource = this.getClass().getResource("/" + fileName);
				InputStream in = null;
				try {
					in = resource.openStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (in != null) {
					this.logger.info("Using config file: " + fileName);
					lemoConfig = (LemoConfig) jaxbUnmarshaller.unmarshal(in);
				}
			}
		} catch (final JAXBException e) {
			// no way to recover, re-throw at runtime
			throw new RuntimeException(e);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (lemoConfig == null) {
			final String files = fileNames.toString();
			throw new RuntimeException(
					"No config file found in the classpath."
							+ " Config file names are based on the context url"
							+ " and follow tomcat's naming convention for .war"
							+ " files. Tried to read files in following order: "
							+ files.substring(1, files.length() - 1));
		}

		this.logger.info("Config loaded for '"
				+ lemoConfig.dataManagementServer.name + "'");
		return lemoConfig;
	}

	private List<IConnector> createConnectors(
			final List<Connector> connectorConfigurations) {
		final List<IConnector> result = Lists.newArrayList();
		for (final Connector connectorConfig : connectorConfigurations) {
			this.logger.info("Inititalizing connector: " + connectorConfig);

			final ESourcePlatform platform = ESourcePlatform
					.valueOf(connectorConfig.platformType);
			final DBConfigObject config = this.createDBConfig(connectorConfig.properties);
			result.add(platform.newConnector(connectorConfig.platformId,
					connectorConfig.name, config));
		}
		return result;
	}

	private DBConfigObject createDBConfig(final List<PropertyConfig> properties) {
		final HashMap<String, String> propertyMap = Maps.newHashMap();
		this.logger.debug("Properties: " + propertyMap.size());
		for (final PropertyConfig property : properties) {
			this.logger.debug(" " + property.key + ":\t" + property.value);
			propertyMap.put(property.key, property.value);
		}
		return new DBConfigObject(propertyMap);
	}

	/**
	 * Gets the server start time or last context reload.
	 * 
	 * @return timestamp of server start
	 */
	public long getStartTime() {
		return this.startTime;
	}

	/**
	 * Sets the server start time.
	 * 
	 * @param startTime
	 *            timestamp of server start
	 */
	public void setStartTime(final long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the mining database's handler.
	 * 
	 * @return a mining database handler
	 */
	public IDBHandler getMiningDbHandler() {
		return this.miningDbHandler;
	}

	/**
	 * Gets the applications name.
	 * 
	 * @return the servers/applications name
	 */
	public String getName() {
		return this.serverName;
	}

}
