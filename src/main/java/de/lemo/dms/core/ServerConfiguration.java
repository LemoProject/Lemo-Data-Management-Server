package de.lemo.dms.core;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.Unmodifiable;
import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;

import de.lemo.dms.core.config.Config;
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

    private Config config;
    private Logger logger = Logger.getLogger(getClass());

    public static ServerConfiguration getInstance() {
        return INSTANCE;
    }

    /**
     * The context path is used to determine the configuration file's name. The first char (a slash by convention) will be
     * ignored. For context paths that aren't valid file name (like the root context or those with sub-paths), tomcat's
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
    protected void loadConfig(String contextPath) {
        String warName;
        if(contextPath.isEmpty()) // empty means root context
            warName = "/ROOT"; // tomcat convention for root context war files is ROOT.war

        // remove leading slash, replace any slashes with hashes like tomcat does
        warName = contextPath.substring(1).replace('/', '#');

        List<String> fileNames = Lists.newArrayList();
        // default, based on war name
        fileNames.add(warName + ".xml");

        if(warName.endsWith("#dms")) {
            fileNames.add(warName.substring(0, warName.length() - "#dms".length()) + ".xml");
            // try to read more generic name by removing trailing '/dms' sub-paths
            // (useful to read the appserver's config file, so only one file is needed)
        }

        if(!fileNames.contains("lemo.xml")) {
            // eventually try generic lemo.xml for use in local development
            fileNames.add("lemo.xml");
        }

        for(String fileName : fileNames) {
            logger.info("Trying to reading config file:" + fileName);
            InputStream in = getClass().getResourceAsStream("/" + fileName);
            if(in == null) {
                logger.info("File not found.");
                continue;
            }
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                config = (Config) jaxbUnmarshaller.unmarshal(in);
            } catch (JAXBException e) {
                // no way to recover, re-throw at runtime
                throw new RuntimeException(e);
            }
        }
        if(config == null) {
            throw new RuntimeException("No valid config file found.");
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

    protected void setStartTime(long timestamp) {
        // TODO Auto-generated method stub

    }

    public Config getConfig() {
        return config;
    }

}
