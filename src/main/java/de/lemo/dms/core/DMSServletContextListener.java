/**
 * File ./main/java/de/lemo/dms/core/DMSServletContextListener.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

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

	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final ServletContext servletContext = sce.getServletContext();
		final ServerConfiguration config = ServerConfiguration.getInstance();

		this.logger.info("Context initialized");
		this.logger.info("ServerInfo:  " + servletContext.getServerInfo());
		this.logger.info("ContextPath: " + servletContext.getContextPath());

		config.loadConfig(servletContext.getContextPath());
		config.setStartTime(new Date().getTime());
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		this.logger.info("Context destroyed");
	}

}