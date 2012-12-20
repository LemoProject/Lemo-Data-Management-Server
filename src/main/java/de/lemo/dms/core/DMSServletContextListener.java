package de.lemo.dms.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author Leonard Kappe
 * 
 */
public class DMSServletContextListener implements ServletContextListener
{

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServerConfiguration config = ServerConfiguration.getInstance();
        ServletContext servletContext = sce.getServletContext();
        Logger log = Logger.getLogger(getClass());
        log.info("Context initialized");
        log.info("ServerInfo:  " + servletContext.getServerInfo());
        log.info("ContextPath: " + servletContext.getContextPath());

        config.loadConfig(servletContext.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger(getClass()).info("Context destroyed");
    }

}
