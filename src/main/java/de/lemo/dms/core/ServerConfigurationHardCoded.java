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
 * Implementierung der Server Konfiguration als Singleton, mit Hard codierten, sowie durch {@link ConfigurationProperties}
 * geladene Einstellungen.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class ServerConfigurationHardCoded implements IServerConfiguration {
    private static IServerConfiguration instance = null;
    private Level level;
    private Logger logger;
    private long startTime;

    private IDBHandler dbHandler;
    private DBConfigObject sourceDBConfig;

    private Level defaultLevel = Level.toLevel(ConfigurationProperties.getPropertyValue("logger.level"), Level.INFO); //$NON-NLS-1$
    private String logfileName = "./DatamanagementServer.log";
    private int port = 8081;
    private int keepAlive = 180;

    // ------------------------------------

    // Singleton Pattern
    public static IServerConfiguration getInstance() {
        if(instance == null) {
            ServerConfigurationHardCoded serverConfigurationHardCoded = new ServerConfigurationHardCoded();
            instance = serverConfigurationHardCoded;
            serverConfigurationHardCoded.initConfig();

            Logger logger = instance.getLogger();
            logger.info("DMS startet at " + new Date(instance.getStartTime()));
            logger.info("Profile " +
                    ApplicationProperties.getPropertyValue("lemo.display-name") +
                    " [" + ApplicationProperties.getPropertyValue("lemo.system-name") + "]");
        }
        return instance;
    }

    private ServerConfigurationHardCoded() {
        startTime = new Date().getTime();
        // logger configuration
        try {
            logger = Logger.getRootLogger();
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
    }

    /*
     * All classes that access the logger should be initialized in this method and not in the constructor to avoid
     * duplication of this class' singleton (and thus duplicated log messages).
     */
    protected void initConfig() {
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
