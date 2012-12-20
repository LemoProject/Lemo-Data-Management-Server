package de.lemo.dms.core;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

/**
 * Interface für die Konfiguration des Servers. Über das Interface werden alle Einstellungen vorgenommen die beim Start des
 * Servers von Relevanz sind und Informationen über den Server bereitgestellt.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * 
 */
public interface IServerConfiguration {

    IDBHandler getDBHandler();

    DBConfigObject getSourceDBConfig();

    long getStartTime();

    void setStartTime(long timestamp);

}
