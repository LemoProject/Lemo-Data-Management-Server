package de.lemo.dms.core;

import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.HibernateDBHandler;

/**
 * Server configuration singleton. Some values are hard coded, some provided by {@link ConfigurationProperties}.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public enum ServerConfigurationHardCoded implements IServerConfiguration {
    
    INSTANCE;
    
    private Level level;
    private Logger logger;
    private long startTime;
    private IDBHandler dbHandler;
    private DBConfigObject sourceDBConfig;

    private Level defaultLevel = Level.toLevel(ConfigurationProperties.getPropertyValue("logger.level"), Level.INFO); //$NON-NLS-1$
    private String logfileName = "./DatamanagementServer.log";
    private int port = 8081;
    private int keepAlive = 180;

    public static IServerConfiguration getInstance() {
        return INSTANCE;
    }

    private ServerConfigurationHardCoded() {
        startTime = new Date().getTime();
        // logger configuration
        try {
            Logger logger = Logger.getRootLogger();
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern(ConfigurationProperties.getPropertyValue("logger.pattern")); //$NON-NLS-1$
            ConsoleAppender conapp = new ConsoleAppender(layout);
            logger.addAppender(conapp);
            FileAppender filapp = new FileAppender(layout, logfileName);
            logger.addAppender(filapp);
            logger.setLevel(defaultLevel);
        } catch (Exception ex) {
            System.err.println("logger can't be initialized...");
            System.err.println(ex.getMessage());
        }
        
        logger = Logger.getLogger(ServerConfigurationHardCoded.class);
        logger.info("DMS running | Profile " +
                ApplicationProperties.getPropertyValue("lemo.display-name") +
                " [" + ApplicationProperties.getPropertyValue("lemo.system-name") + "]");

        initDbConfig();
        dbHandler = new HibernateDBHandler();
    }

    private void initDbConfig() {
        // Setting up source database
        sourceDBConfig = new DBConfigObject();

        sourceDBConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        sourceDBConfig.addProperty("hibernate.hbm2ddl.auto", "update");

        String sourcePrefix = "source";

        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.driver_class");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.url");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.username");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.password");

        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.show_sql");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.format_sql");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.use_sql_comments");
        addDBProperty(sourceDBConfig, sourcePrefix, "log4j.logger.org.hibernate");

    }

    private void addDBProperty(DBConfigObject dbConfig, String propertyPrefix, String propertyName) {
        dbConfig.addProperty(propertyName,
            ConfigurationProperties.getPropertyValue(propertyPrefix + "." + propertyName));
    }

    public IDBHandler getDBHandler()
    {
        return dbHandler;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public Level getLoggingLevel() {
        return this.level;
    }

    @Override
    public void setLoggingLevel(Level level) {
        this.level = level;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(long time) {
        this.startTime = time;
    }

    @Override
    public int getRemotePort() {
        return port;
    }

    @Override
    public void setRemotePort(int port) {
        this.port = port;
    }

    @Override
    public DBConfigObject getSourceDBConfig() {
        return sourceDBConfig;
    }

    @Override
    public int getKeepAliveTimeoutInSec() {
        return keepAlive;
    }

}
