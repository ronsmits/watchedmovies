package com.example.utils;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.ejb.Configuration;

/**
 * After the start of the application, initialize the {@link Configuration} bean with the servletContext.
 *
 */
@WebListener
public class StartListener implements ServletContextListener {
	@EJB private Configuration configuration;
    /**
     * Default constructor. 
     */
    public StartListener() {
        
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        configuration.setContext(servletContext);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        
    }
	
}
