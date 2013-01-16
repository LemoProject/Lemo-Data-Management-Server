package de.lemo.dms.core;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;

/**
 * Server instance which can be run as a java application.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public enum DMSRemoteServer {

    INSTANCE;
    
    private String host = "localhost";
    private IServerConfiguration config;
    private HttpServer server;
    private Logger logger;

    private DMSRemoteServer() {
        config = ServerConfigurationHardCoded.getInstance();
        logger = config.getLogger();
    }

    /**
     * Stop it.
     */
    private void stop() {
        if(server != null) {
            server.stop();
            logger.info("remote server stopped...");
            server = null;
        }
    }

    /**
     * Stop and start the server again.
     * 
     * @throws IllegalArgumentException
     * @throws IOException
     */
    protected void restart() throws IOException {
        this.stop();
        this.start();
    }

    /**
     * Start and initialize the server if it isn't already running.
     * 
     * @throws IllegalArgumentException
     * @throws IOException
     */
    protected void start() throws IOException {
        int port = config.getRemotePort();
        if(server == null) {
            server = GrizzlyServerFactory.createHttpServer("http://" + host + ":" + port, new DMSResourceConfig());
            server.start();
            logger.info("remote server start... host: " + host + " on port " + port);
            Version v  = new Version();
            logger.info("version: " + v.getServerVersion());
        }
        else {
            logger.info("remote server already started... host: " + host + " on port " + port);
        }
    }
}
