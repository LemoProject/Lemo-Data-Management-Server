package de.lemo.dms.core;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

/**
 * Interface für die Konfiguration des Servers. Über
 * das Interface werden alle Einstellungen vorgenommen
 * die beim Start des Servers von Relevanz sind und
 * Informationen über den Server bereitgestellt.
 * @author Boris Wenzlaff
 *
 */
public interface IServerConfiguration {
	Logger getLogger();
	Level getLoggingLevel();
	IDBHandler getDBHandler();

	DBConfigObject getSourceDBConfig();

	void setLoggingLevel(Level level);
	/**
	 * @return Startzeit des Servers
	 */
	long getStartTime();
	void setStartTime(long time);
	/**
	 * @return Port für den Remote Server
	 */
	int getRemotePort();
	
	/**
	 * 
	 * @return the server timeout in seconds
	 */
	int getKeepAliveTimeoutInSec();
	
	/**
	 * Setzt den Port fürt den Remote Server
	 * @param port für Remote Server
	 */
	void setRemotePort(int port);
}
