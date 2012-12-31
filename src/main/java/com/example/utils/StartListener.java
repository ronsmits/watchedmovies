package com.example.utils;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.ejb.Configuration;

/**
 * Application Lifecycle Listener implementation class StartListener
 *
 */
@WebListener
public class StartListener implements ServletContextListener {
	@EJB private Configuration configuration;
    /**
     * Default constructor. 
     */
    public StartListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext servletContext = arg0.getServletContext();
        System.out.println(servletContext.getContextPath());
        configuration.setContext(servletContext);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
