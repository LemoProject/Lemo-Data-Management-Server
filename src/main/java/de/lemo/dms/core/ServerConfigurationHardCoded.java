package de.lemo.dms.core;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.hibernate.Session;

import com.google.common.collect.Lists;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.HibernateDBHandler;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.service.BaseService;

/**
 * Implementierung der Server Konfiguration als Singleton, mit Hard codierten,
 * sowie durch {@link ConfigurationProperties} geladene Einstellungen.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class ServerConfigurationHardCoded implements IServerConfiguration {
    private static IServerConfiguration instance = null;
    private Level level;
    private Logger logger;
    private long startTime;
    private DBConfigObject miningDBConfig;
    private IDBHandler dbHandler;
    private DBConfigObject sourceDBConfig;
    private DMSResourceConfig resourceConfig;
    // ------------------------------------
    // Hard codierte Konfiguration
    // private String loggerName = "lemo.dms";
    private Level defaultLevel = Level.toLevel(ConfigurationProperties.getString("logger.level"), Level.INFO); //$NON-NLS-1$
    private String logfileName = "./DatamanagementServer.log";
    private int port = 4443;
    private int keepAlive = 180;

    // ------------------------------------

    // Singleton Pattern
    // Hard codierte Einstellungen
    private ServerConfigurationHardCoded() {
        // logger konfiguration
        try {

            logger = Logger.getRootLogger();
            // SimpleLayout layout = new SimpleLayout();
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern(ConfigurationProperties.getString("logger.pattern")); //$NON-NLS-1$
            ConsoleAppender conapp = new ConsoleAppender(layout);
            logger.addAppender(conapp);
            FileAppender filapp = new FileAppender(layout, logfileName);
            logger.addAppender(filapp);
            logger.setLevel(defaultLevel);
        } catch (Exception ex) {
            System.err.println("logger can't be initialize...");
            System.err.println(ex.getMessage());
        }
        startTime = new Date().getTime();

    }

    /*
     * All classes that access the logger should be initialized in this method
     * and not in the constructor to avoid duplication of this class' singleton
     * (and thus duplicated log messages).
     */
    protected void initConfig() {
        resourceConfig = new DMSResourceConfig();
        initDbConfig();
        dbHandler = new HibernateDBHandler();
    }

    private void initDbConfig() {
        // Setting up source database
        sourceDBConfig = new DBConfigObject();

        // sourceDBConfig.addProperty("hibernate.connection.provider_class",
        // "org.hibernate.connection.C3P0ConnectionProvider");
        // sourceDBConfig.addProperty("hibernate.cache.use_second_level_cache",
        // "false");
        sourceDBConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        sourceDBConfig.addProperty("hibernate.hbm2ddl.auto", "update");

        String sourcePrefix = "source";

        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.driver_class");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.url");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.username");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.connection.password");

        // addDBProperty(sourceDBConfig, sourcePrefix,
        // "hibernate.c3p0.min_size");
        // addDBProperty(sourceDBConfig, sourcePrefix,
        // "hibernate.c3p0.max_size");
        // addDBProperty(sourceDBConfig, sourcePrefix,
        // "hibernate.c3p0.timeout");
        // addDBProperty(sourceDBConfig, sourcePrefix,
        // "hibernate.c3p0.max_statements");
        // addDBProperty(sourceDBConfig, sourcePrefix,
        // "hibernate.c3p0.idle_test_period");

        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.show_sql");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.format_sql");
        addDBProperty(sourceDBConfig, sourcePrefix, "hibernate.use_sql_comments");
        addDBProperty(sourceDBConfig, sourcePrefix, "log4j.logger.org.hibernate");

        // Setting up mining database
        miningDBConfig = new DBConfigObject();

        miningDBConfig.addProperty("hibernate.connection.provider_class",
            "org.hibernate.connection.C3P0ConnectionProvider");

        miningDBConfig.addProperty("hibernate.cache.use_second_level_cache", "false");
        miningDBConfig.addProperty("hibernate.cache.use_query_level_cache", "false");
        miningDBConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        // miningDBConfig.addProperty("hibernate.hbm2ddl.auto", "update");

        String miningPrefix = "mining";

        addDBProperty(miningDBConfig, miningPrefix, "hibernate.connection.driver_class");
        addDBProperty(miningDBConfig, miningPrefix, "hibernate.connection.url");
        addDBProperty(miningDBConfig, miningPrefix, "hibernate.connection.username");
        addDBProperty(miningDBConfig, miningPrefix, "hibernate.connection.password");

        addDBProperty(miningDBConfig, miningPrefix,
            "hibernate.c3p0.min_size");
        addDBProperty(miningDBConfig, miningPrefix,
            "hibernate.c3p0.max_size");
        addDBProperty(miningDBConfig, miningPrefix,
            "hibernate.c3p0.timeout");
        addDBProperty(miningDBConfig, miningPrefix,
            "hibernate.c3p0.max_statements");
        addDBProperty(miningDBConfig, miningPrefix,
            "hibernate.c3p0.idle_test_period");

        addDBProperty(miningDBConfig, miningPrefix, "hibernate.show_sql");
        addDBProperty(miningDBConfig, miningPrefix, "hibernate.format_sql");
        addDBProperty(miningDBConfig, miningPrefix, "hibernate.use_sql_comments");
        addDBProperty(miningDBConfig, miningPrefix, "log4j.logger.org.hibernate");
        // addDBProperty(miningDBConfig,miningPrefix,"hibernate.search.indexing_strategy");
    }

    private void addDBProperty(DBConfigObject dbConfig, String propertyPrefix, String propertyName) {
        dbConfig.addProperty(propertyName,
            ConfigurationProperties.getString(propertyPrefix + "." + propertyName));
    }

    public IDBHandler getDBHandler()
    {
        return dbHandler;
    }

    public DBConfigObject getMiningDBConfig()
    {
        return miningDBConfig;
    }

    // Singleton Pattern
    public static IServerConfiguration getInstance() {
        if(instance == null) {
            ServerConfigurationHardCoded serverConfigurationHardCoded = new ServerConfigurationHardCoded();
            instance = serverConfigurationHardCoded;
            serverConfigurationHardCoded.initConfig();

            Logger logger = instance.getLogger();
            System.out.println("DMS config at " + new Date(instance.getStartTime()));

            // // session test
            // IDBHandler dbh = instance.getDBHandler();
            // System.out.println("DBHandler test: " + dbh);
            // Session miningSession = dbh.getMiningSession();
            // miningSession.createCriteria(Object.class).setMaxResults(1).list();
            // System.out.println("Session test: " + miningSession);
            // miningSession.close();
        }

        return instance;
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

    // @Override
    // public DMSResourceConfig getResourceConfig() {
    // return resourceConfig;
    // }

    @Override
    public int getKeepAliveTimeoutInSec() {
        return keepAlive;
    }

}
