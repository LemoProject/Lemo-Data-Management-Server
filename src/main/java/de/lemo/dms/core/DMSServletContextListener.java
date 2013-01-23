package de.lemo.dms.core;

import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;

/**
 * Receives notification when changes to the servlet context are made and
 * (re)loads the DMS configuration file.
 * 
 * @author Leonard Kappe
 */
public class DMSServletContextListener implements ServletContextListener {

	private Logger logger;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		ServerConfiguration config = ServerConfiguration.getInstance();

		logger = Logger.getLogger(getClass());
		logger.info("Context initialized");
		logger.info("ServerInfo:  " + servletContext.getServerInfo());
		logger.info("ContextPath: " + servletContext.getContextPath());

		config.loadConfig(servletContext.getContextPath());
		config.setStartTime(new Date().getTime());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Context destroyed");
	}

}