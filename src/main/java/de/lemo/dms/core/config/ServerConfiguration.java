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
import org.hibernate.cfg.Configuration;

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
        Logger.getRootLogger().setLevel(Level.INFO);
        Logger.getRootLogger().addAppender(new ConsoleAppender(
                new PatternLayout("[%p] %d{ISO8601} [%c{1}] - %m%n")));
    }

    private Configuration miningConfig;
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
        if(contextPath.isEmpty()) {
            // empty means root context, Tomcat convention for root context war files is 'ROOT.war'
            contextPath = "/ROOT";
        }

        // remove leading slash, replace any slashes with hashes like Tomcat does with the war files
        String warName = contextPath.substring(1).replace('/', '#');

        Set<String> fileNames = new LinkedHashSet<String>();
        // default, based on war name
        fileNames.add(warName + ".xml");

        int lastHash;
        String warPath = warName;
        while((lastHash = warPath.lastIndexOf('#')) > 0) {
            // try to read more generic name by removing sub-paths, i.e. /lemo/dms -> /lemo
            // (useful to read the appserver's config file, so only one file is needed)
            warPath = warPath.substring(0, lastHash);
            fileNames.add(warPath + ".xml");
        }

        // eventually try generic lemo.xml for use in local development
        fileNames.add("lemo.xml");

        LemoConfig lemoConfig = null;
        for(String fileName : fileNames) {
            InputStream in = getClass().getResourceAsStream("/" + fileName);
            if(in == null) {
                continue;
            }
            logger.info("Using config file: " + fileName);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(LemoConfig.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                lemoConfig = (LemoConfig) jaxbUnmarshaller.unmarshal(in);
            } catch (JAXBException e) {
                // no way to recover, re-throw at runtime
                throw new RuntimeException(e);
            }
        }

        if(lemoConfig == null) {
            String files = fileNames.toString();
            throw new RuntimeException(
                    "No config file found in the classpath. Files follow tomcat's naming convention for .war files, " +
                            "in following order until a valid one is found: "
                            + files.substring(1, files.length() - 1));
        }

        logger.info("Config loaded for '" + lemoConfig.dataManagementServer.name + "'");

        // TODO set level from config
        // Logger.getRootLogger().setLevel(Level.INFO);

        createMiningConfig(lemoConfig.dataManagementServer.hibernateConfig);
        createConnectors(lemoConfig.dataManagementServer.connectors);
    }

    private void createConnectors(List<Connector> connectors) {
        // TODO Auto-generated method stub

    }

    private void createMiningConfig(List<HibernateProperty> lemoHibernateConfig) {
        miningConfig = new Configuration();
        for(HibernateProperty property : lemoHibernateConfig) {
            miningConfig.setProperty(property.name, property.value);
        }
    }

    public IDBHandler getDBHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    public DBConfigObject getSourceDBConfig() {
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

    public Configuration getMiningConfig() {
        return miningConfig;
    }

}
