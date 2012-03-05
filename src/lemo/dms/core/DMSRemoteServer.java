package lemo.dms.core;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

/**
 * Der Server der von außen erreichbar sein wird. Er stellt
 * REST Interfaces für die Steuerung des Servers bereit.
 * @author Boris Wenzlaff
 *
 */
public class DMSRemoteServer {
	private int port = 0;
	private String host = "localhost";
	private static DMSRemoteServer instance = null;
	private IServerConfiguration config = null;
	private SelectorThread server = null;
	private Logger logger = null;
	
	/**
	 * Singleton Pattern
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	private DMSRemoteServer() {
		config = ServerConfigurationHardCoded.getInstance();
		logger = config.getLogger();
	}
	
	/**
	 * Singleton Pattern
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
			server.stopEndpoint();
			logger.info("remote server stopped...");
			server = null;
		}
	}
	
	/**
	 * Stoppt den Server und startet ihn neu
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	protected void restart() throws IllegalArgumentException, IOException {
		this.stop();
		this.start();
	}
	
	/**
	 * Startet den Server sofern er noch nicht gestartet wurde.
	 * Die Konfiguration wird über das IServerConfiguration eingestellt.
	 * @throws IllegalArgumentException für die Instanzierung des Servers
	 * @throws IOException für die Instanzierung des Servers
	 */
	protected void start() throws IllegalArgumentException, IOException {
		port = config.getRemotePort();
		if (server == null) {
			server = GrizzlyServerFactory.create("http://"+host+":"+ port);
			logger.info("remote server start... host: " + host + " on port " + port);
		}
		else {
			logger.info("remote server already started... host: " + host + " on port " + port);
		}
	}
}
