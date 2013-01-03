package de.lemo.dms.core.config;

import java.io.InputStream;
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

import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.ESourcePlatform;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

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
        Logger.getRootLogger().addAppender(new ConsoleAppender(
                new PatternLayout("[%p] %d{ISO8601} [%c{1}] - %m%n")));
    }

    private DBConfigObject miningDBConfig;
    private Logger logger = Logger.getLogger(getClass());

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

        miningDBConfig = createDBConfig(lemoConfig.dataManagementServer.hibernateConfig);

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

    private List<IConnector> createConnectors(List<ConnectorConfig> connectorConfigurations) {

        List<IConnector> result = Lists.newArrayList();
        for(ConnectorConfig connectorConfig : connectorConfigurations) {
            logger.info("Connector: " + connectorConfig.name);
            ESourcePlatform platform = ESourcePlatform.valueOf(connectorConfig.platformType);
            Class<? extends IConnector> connectorType = platform.getConnectorType();
            IConnector connector = null;
            try {
                connector = connectorType.newInstance();
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
            connector.setSourceDBConfig(createDBConfig(connectorConfig.hibernateConfig));
            result.add(connector);
        }
        return result;
    }

    private DBConfigObject createDBConfig(List<HibernatePropertyConfig> lemoHibernateConfig) {
        DBConfigObject result = new DBConfigObject();
        for(HibernatePropertyConfig property : lemoHibernateConfig) {
            result.setProperty(property.name, property.value);
        }
        return result;
    }

    public IDBHandler getDBHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    public long getStartTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setStartTime(long timestamp) {
        // TODO Auto-generated method stub

    }
//    public DBConfigObject getMiningDBConfig() {
//        return miningDBConfig;
//    }

}
