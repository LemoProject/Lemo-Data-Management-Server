package de.lemo.dms.core;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;

/**
 * Der Server der von außen erreichbar sein wird. Er stellt REST Interfaces für die Steuerung des Servers bereit.
 * 
 * @author Boris Wenzlaff
 * 
 */
public class DMSRemoteServer {

    private String host = "localhost";
    private static DMSRemoteServer instance;
    private IServerConfiguration config;
    private HttpServer server;
    private Logger logger;

    /**
     * Singleton Pattern
     * 
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private DMSRemoteServer() {
        config = ServerConfigurationHardCoded.getInstance();
        logger = config.getLogger();
    }

    /**
     * Singleton Pattern
     * 
     * @return Instanz des Datamanagement Servers
     * @throws IOException
     * @throws IllegalArgumentException
     */
    protected static DMSRemoteServer getInstance() {
        if(instance == null) {
            instance = new DMSRemoteServer();
        }
        return instance;
    }

    /**
     * Stoppt den Server
     */
    private void stop() {
        if(server != null) {
            server.stop();
            logger.info("remote server stopped...");
            server = null;
        }
    }

    /**
     * Stoppt den Server und startet ihn neu
     * 
     * @throws IllegalArgumentException
     * @throws IOException
     */
    protected void restart() throws IOException {
        this.stop();
        this.start();
    }

    /**
     * Startet den Server sofern er noch nicht gestartet wurde. Die Konfiguration wird über das IServerConfiguration
     * eingestellt.
     * 
     * @throws IllegalArgumentException
     *             für die Instanzierung des Servers
     * @throws IOException
     *             für die Instanzierung des Servers
     */
    protected void start() throws IOException {
        int port = config.getRemotePort();
        if(server == null) {
            server = GrizzlyServerFactory.createHttpServer("http://" + host + ":" + port, new DMSResourceConfig());
            server.start();
            logger.info("remote server start... host: " + host + " on port " + port);
        }
        else {
            logger.info("remote server already started... host: " + host + " on port " + port);
        }
    }
}
