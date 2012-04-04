package de.lemo.dms.core;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Die Klasse mit der Main-Methode für den DataManagementServer.
 * Über sie wird der Server gestartet.
 * @author Boris Wenzlaff
 *
 */
public class DMSMain {

public static void main(String[] args) {
	DMSRemoteServer remoteServer = DMSRemoteServer.getInstance();
	Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	IServerConfiguration config = ServerConfigurationHardCoded.getInstance();
	
	config.setStartTime(new Date().getTime());
	
	try {
		remoteServer.start();
	} catch (IllegalArgumentException e) {
		logger.error("remote server could not be started... " + e.getMessage());
		System.exit(1);
	} catch (IOException e) {
		logger.error("remote server could not be started... " + e.getMessage());
		//Abbruch mit Exit Status 1
		System.exit(1);
	}

	/**
	 * Endlosschleife, stoppen des gesamten Servers über STRG + C
	 */
	while(true) {
		
	}
}
}
