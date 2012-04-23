package de.lemo.dms.core;

import org.apache.log4j.*;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.HibernateDBHandler;

/**
 * Implementierung der Server Konfiguration als Singleton, mit
 * Hard codierten Einstellungen.
 * 
 * @author Boris Wenzlaff
 *
 */
public class ServerConfigurationHardCoded implements IServerConfiguration{
	private static IServerConfiguration instance = null;
	private Level level = null;
	private Logger logger = null;
	private long startTime = 0;
	private DBConfigObject dbConfig = null;
	private IDBHandler dbHandler = null;
	private DBConfigObject sourceDBConfig = null;
	//------------------------------------
	//Hard codierte Konfiguration
	private String loggerName = "lemo.dms";
	private Level defaultLevel = Level.OFF;
	private String logfileName = "./DatamanagementServer.log";
	private int port = 4443;
	//------------------------------------
		
	// Singleton Pattern
	// Hard codierte Einstellungen
	private ServerConfigurationHardCoded() {
		// logger konfiguration
		try {
			logger = Logger.getRootLogger();
			//SimpleLayout layout = new SimpleLayout();
			PatternLayout layout = new PatternLayout();
			layout.setConversionPattern( "%p [%d{dd MMM yyyy HH:mm:ss,SSS}] [%C] [l:%L]- %m%n");
			ConsoleAppender conapp = new ConsoleAppender(layout);
			logger.addAppender(conapp);
			FileAppender filapp = new FileAppender(layout, logfileName);
			logger.addAppender(filapp);
			logger.setLevel(defaultLevel);
			
			//Setting up source database
			sourceDBConfig = new DBConfigObject();

			sourceDBConfig.addProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			sourceDBConfig.addProperty("hibernate.connection.url", "jdbc:mysql://localhost/lmsmoodle");
			sourceDBConfig.addProperty("hibernate.connection.username", "datamining");
			sourceDBConfig.addProperty("hibernate.connection.password", "LabDat1#");
			
			sourceDBConfig.addProperty("hibernate.c3p0.min_size", "5");
			sourceDBConfig.addProperty("hibernate.c3p0.max_size", "20");		
			sourceDBConfig.addProperty("hibernate.c3p0.timeout", "300");
			sourceDBConfig.addProperty("hibernate.c3p0.max_statements", "50");
			sourceDBConfig.addProperty("hibernate.c3p0.idle_test_period", "3000");
			
			sourceDBConfig.addProperty("hibernate.cache.use_second_level_cache", "false");		
			
			//sourceConf.addProperty("hibernate.show_sql", "true");
			//sourceConf.addProperty("hibernate.format_sql", "true");
			//sourceConf.addProperty("hibernate.use_sql_comments", "true");
			
			sourceDBConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

			
			//Setting up mining database
			dbConfig = new DBConfigObject();
			dbConfig.addProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");		
			dbConfig.addProperty("hibernate.connection.url", "jdbc:mysql://localhost/dmtest");
			dbConfig.addProperty("hibernate.connection.username", "datamining");
			dbConfig.addProperty("hibernate.connection.password", "LabDat1#");
			
			dbConfig.addProperty("hibernate.c3p0.min_size", "5");
			dbConfig.addProperty("hibernate.c3po.max_size", "20");
			dbConfig.addProperty("hibernate.c3p0.timeout", "300");
			dbConfig.addProperty("hibernate.c3p0.max_statements", "50");
			dbConfig.addProperty("hibernate.c3p0.idle_test_period", "3000");
			
			dbConfig.addProperty("hibernate.cache.use_second_level_cache", "false");
			dbConfig.addProperty("hibernate.cache.use_query_level_cache", "false");		
			
			//miningConf.addProperty("hibernate.show_sql", "false");
			//miningConf.addProperty("hibernate.format_sql", "false");
			//miningConf.addProperty("hibernate.use_sql_comments", "true");					
			dbConfig.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			//miningConf.addProperty("hibernate.hbm2ddl.auto","update");
			
			//Setting up dbHandler
			dbHandler = new HibernateDBHandler();

			
		} catch (Exception ex) {
			System.err.println("logger can't be initialize...");
			System.err.println(ex.getMessage());
		}		
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
			instance = new ServerConfigurationHardCoded();
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
		// TODO Auto-generated method stub
		return sourceDBConfig;
	}

}
