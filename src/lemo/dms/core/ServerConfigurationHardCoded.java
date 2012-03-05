package lemo.dms.core;

import org.apache.log4j.*;

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
	//------------------------------------
	//Hard codierte Konfiguration
	private String loggerName = "lemo.dms";
	private Level defaultLevel = Level.ALL;
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
		} catch (Exception ex) {
			System.err.println("logger can't be initialize...");
			System.err.println(ex.getMessage());
		}		
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

}
