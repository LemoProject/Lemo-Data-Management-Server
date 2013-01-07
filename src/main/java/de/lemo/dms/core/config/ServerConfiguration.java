package de.lemo.dms.core.config;

import java.io.InputStream;
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
import de.lemo.dms.db.hibernate.MiningHibernateUtil;
import de.lemo.dms.test.HibernateDBHandler;

/**
 * 
 * @author Leonard Kappe
 * 
 */
public enum ServerConfiguration {

    INSTANCE;

    {
        // the very first place where we can initialize the logger
        Logger.getRootLogger().setLevel(Level.INFO);
        Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout("[%p] %d{ISO8601} [%c{1}] - %m%n")));
    }

    private Logger logger = Logger.getLogger(getClass());
    private IDBHandler miningDbHandler;
    private long startTime;

    public static ServerConfiguration getInstance() {
        return INSTANCE;
    }

    /**
     * The context path is used to determine the configuration file's name. The first char (a slash by convention) will be
     * ignored. Some context paths aren't valid file names (like the root context or those with sub-paths), so tomcat's
     * naming conventions for .war files will be used.
     * 
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
     * 
     * @param contextPath
     */
    public void loadConfig(String contextPath) {
        LemoConfig lemoConfig = readConfigFiles(contextPath);
        // TODO set log level from config

        logger.info("Inititalizing mining database");
        DBConfigObject miningDBConfig = createDBConfig(lemoConfig.dataManagementServer.databaseProperties);
        MiningHibernateUtil.initSessionFactory(miningDBConfig);
        miningDbHandler = new HibernateDBHandler();
        
        List<IConnector> connectors = createConnectors(lemoConfig.dataManagementServer.connectors);
        ConnectorManager connectorManager = ConnectorManager.getInstance();
        for(IConnector connector : connectors) {
            connectorManager.addConnector(connector);
        }
    }

    private LemoConfig readConfigFiles(String contextPath) {
        if(contextPath.isEmpty()) {
            // empty means root context, Tomcat convention for root context war files is 'ROOT.war'
            contextPath = "/ROOT";
        }

        // remove leading slash, replace any slashes with hashes like Tomcat does with the war files
        String warName = contextPath.substring(1).replace('/', '#');

        Set<String> fileNames = new LinkedHashSet<String>();
        fileNames.add(warName + ".xml"); // default, based on war name

        int lastHash;
        String warPath = warName;
        while((lastHash = warPath.lastIndexOf('#')) > 0) {
            // try to read more generic name by removing sub-paths, i.e. /lemo/dms -> /lemo
            // (useful to read the appserver's config file, so only one file is needed)
            warPath = warPath.substring(0, lastHash);
            fileNames.add(warPath + ".xml");
        }

        fileNames.add("lemo.xml"); // eventually try generic lemo.xml for use in local development

        LemoConfig lemoConfig = null;
        try {
            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(LemoConfig.class).createUnmarshaller();
            for(String fileName : fileNames) {
                InputStream in = getClass().getResourceAsStream("/" + fileName);
                if(in != null) {
                    logger.info("Using config file: " + fileName);
                    lemoConfig = (LemoConfig) jaxbUnmarshaller.unmarshal(in);
                }
            }
        } catch (JAXBException e) {
            // no way to recover, re-throw at runtime
            throw new RuntimeException(e);
        }
        if(lemoConfig == null) {
            String files = fileNames.toString();
            throw new RuntimeException(
                    "No config file found in the classpath. Files follow tomcat's naming convention for .war files, " +
                            "in following order until a valid one is found: "
                            + files.substring(1, files.length() - 1));
        }

        logger.info("Config loaded for '" + lemoConfig.dataManagementServer.name + "'");
        return lemoConfig;
    }

    private List<IConnector> createConnectors(List<Connector> connectorConfigurations) {
        List<IConnector> result = Lists.newArrayList();
        for(Connector connectorConfig : connectorConfigurations) {
            logger.info("Inititalizing connector: " + connectorConfig);

            ESourcePlatform platform = ESourcePlatform.valueOf(connectorConfig.platformType);
            DBConfigObject config = createDBConfig(connectorConfig.properties);
            result.add(platform.newConnector(config));
        }
        return result;
    }

    private DBConfigObject createDBConfig(List<PropertyConfig> properties) {
        HashMap<String, String> propertyMap = Maps.newHashMap();
        logger.debug("Properties: " + propertyMap.size());
        for(PropertyConfig property : properties) {
            logger.debug(" " + property.key + ":\t" + property.value);
            propertyMap.put(property.key, property.value);
        }
        return new DBConfigObject(propertyMap);
    }

    public IDBHandler getDBHandler() {
        return null;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public IDBHandler getMiningDbHandler() {
        return miningDbHandler;
    }

}
