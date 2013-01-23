package de.lemo.dms.core;

import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Main entry point for running the server as Java application, i.e. not in a
 * servlet container like tomcat.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @see DMSRemoteServer
 */
public class DMSMain {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(DMSMain.class);

		try {
			DMSRemoteServer.INSTANCE.start();

			// new de.lemo.dms.connectors.Test().run();

			/*
			 * TODO every example uses 'read' to keep the server running but
			 * there may be a better way
			 */
			System.in.read();

		} catch (IOException e) {
			logger.error("DMS start failed.", e);
			System.exit(1);
		}
	}
}
