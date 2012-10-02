package de.lemo.dms.core;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Main entry point for running the server as Java application, i.e. not in a servlet container
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class DMSMain {

    public static void main(String[] args) {
        DMSRemoteServer remoteServer = DMSRemoteServer.getInstance();
        Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();

        try {
            remoteServer.start();
            System.in.read(); // TODO every example uses this to keep the server running but there may be a better way
        } catch (IOException e) {
            logger.error("remote server could not be started... " + e.getMessage());
            System.exit(1);
        }
    }
}
