package de.lemo.dms.core;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.HibernateDBHandler;
import de.lemo.dms.processing.Question;
import de.lemo.dms.service.BaseService;

/**
 * Implementierung der Server Konfiguration als Singleton, mit
 * Hard codierten, sowie durch {@link Configuration} geladene Einstellungen.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class ServerConfigurationHardCoded implements IServerConfiguration{
	private static IServerConfiguration instance = null;
	private Level level = null;
	private Logger logger = null;
	private long startTime = 0;
	private DBConfigObject dbConfig = null;
	private IDBHandler dbHandler = null;
	private DBConfigObject sourceDBConfig = null;
    private DMSResourceConfig resourceConfig;
	//------------------------------------
	//Hard codierte Konfiguration
	// private String loggerName = "lemo.dms";
	private Level defaultLevel = Level.toLevel(Configuration.getString("logger.level"), Level.INFO); //$NON-NLS-1$
	private String logfileName = "./DatamanagementServer.log";
	private int port = 4443;
	private int keepAlive = 180;
	//------------------------------------
		
	// Singleton Pattern
	// Hard codierte Einstellungen
    private ServerConfigurationHardCoded() {
        // logger konfiguration
        try {
            logger = Logger.getRootLogger();
            // SimpleLayout layout = new SimpleLayout();
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern(Configuration.getString("logger.pattern")); //$NON-NLS-1$
            ConsoleAppender conapp = new ConsoleAppender(layout);
            logger.addAppender(conapp);
            FileAppender filapp = new FileAppender(layout, logfileName);
            logger.addAppender(filapp);
            logger.setLevel(defaultLevel);
        } catch (Exception ex) {
            System.err.println("logger can't be initialize..."); 
            System.err.println(ex.getMessage());
        }
       
	}
	
    /*
     * All classes that access the logger should be initialized in this method
     * and not in the constructor to avoid duplication of this class' singleton
     * (and thus duplicated log messages).
     */
    protected void initConfig() {
	    
	    resourceConfig = new DMSResourceConfig(BaseService.class.getPackage(), Question.class.getPackage());
	    
	    //Setting up source database
    	sourceDBConfig = new DBConfigObject();
    
    	//sourceDBConfig.addProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    	sourceDBConfig.addProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
    	sourceDBConfig.addProperty("hibernate.connection.url", Configuration.getString("source.hibernate.connection.url")); //$NON-NLS-1$ //$NON-NLS-2$
    	sourceDBConfig.addProperty("hibernate.connection.username", Configuration.getString("source.hibernate.connection.username")); //$NON-NLS-1$ //$NON-NLS-2$
    	sourceDBConfig.addProperty("hibernate.connection.password", Configuration.getString("source.hibernate.connection.password")); //$NON-NLS-1$ //$NON-NLS-2$
    	
    	sourceDBConfig.addProperty("hibernate.c3p0.min_size", "5");
    	sourceDBConfig.addProperty("hibernate.c3p0.max_size", "20");
    	sourceDBConfig.addProperty("hibernate.c3p0.timeout", "300");
    	sourceDBConfig.addProperty("hibernate.c3p0.max_statements", "50");
    	sourceDBConfig.addProperty("hibernate.c3p0.idle_test_period", "3000");
    	
    	sourceDBConfig.addProperty("hibernate.cache.use_second_level_cache", "false");
    	
    	//sourceDBConfig.addProperty("hibernate.show_sql", "true");
    	//sourceDBConfig.addProperty("hibernate.format_sql", "true");
    	//sourceDBConfig.addProperty("hibernate.use_sql_comments", "true");
    	
    	sourceDBConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	sourceDBConfig.addProperty("hibernate.hbm2ddl.auto","update");
    	
    	//Setting up mining database
    	dbConfig = new DBConfigObject();
    	dbConfig.addProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    	dbConfig.addProperty("hibernate.connection.url", Configuration.getString("mining.hibernate.connection.url")); //hier db namen eintragen //$NON-NLS-1$ //$NON-NLS-2$
    	dbConfig.addProperty("hibernate.connection.username", Configuration.getString("mining.hibernate.connection.username")); //db user //$NON-NLS-1$ //$NON-NLS-2$
    	dbConfig.addProperty("hibernate.connection.password", Configuration.getString("mining.hibernate.connection.password")); //user passwort //$NON-NLS-1$ //$NON-NLS-2$
    	
    	
    	//dbConfig.addProperty("hibernate.search.indexing_strategy", Configuration.getString("mining.hibernate.search.indexing_strategy"));
    	dbConfig.addProperty("hibernate.c3p0.min_size", "5");
    	dbConfig.addProperty("hibernate.c3po.max_size", "20");
    	dbConfig.addProperty("hibernate.c3p0.timeout", "300");
    	dbConfig.addProperty("hibernate.c3p0.max_statements", "50");
    	dbConfig.addProperty("hibernate.c3p0.idle_test_period", "3000");
    	
    	
    	dbConfig.addProperty("hibernate.cache.use_second_level_cache", "false");
    	dbConfig.addProperty("hibernate.cache.use_query_level_cache", "false");
    	
    	//dbConfig.addProperty("hibernate.show_sql", "true");
    	//dbConfig.addProperty("hibernate.format_sql", "true");
    	//dbConfig.addProperty("hibernate.use_sql_comments", "true");					
    	dbConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	//dbConfig.addProperty("hibernate.hbm2ddl.auto","update");
    	
    	//Setting up dbHandler
    	dbHandler = new HibernateDBHandler();
    
	}
	
	public IDBHandler getDBHandler()
	{
		return dbHandler;
	}
	
	public DBConfigObject getMiningDBConfig()
	{
		return dbConfig;
	}
	
	//Singleton Pattern
    public static IServerConfiguration getInstance() {
        if(instance == null) {
            ServerConfigurationHardCoded serverConfigurationHardCoded = new ServerConfigurationHardCoded();
            instance = serverConfigurationHardCoded;
            serverConfigurationHardCoded.initConfig();
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

    @Override
    public DMSResourceConfig getResourceConfig() {
        return resourceConfig;
    }

	@Override
	public int getKeepAliveTimeoutInSec() {
		return keepAlive;
	}

}
