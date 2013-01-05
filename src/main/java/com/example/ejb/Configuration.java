package com.example.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.servlet.ServletContext;

import com.example.utils.JadeEngine;
import com.example.utils.StartListener;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * Singleton bean to feed the context and the JadeConfiguration to beans that need it.
 */
@Singleton
@LocalBean
public class Configuration {
	private ServletContext context;
	private JadeConfiguration configuration = null;
	
    /**
     * Default constructor. 
     */
    public Configuration() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Return the servletContext to beans that need it.
     * @return
     */
	public ServletContext getContext() {
		return context;
	}

    
    /**
     * Setup the context, this will be fed in from the {@link StartListener}. Therefor it is always filled. Once it is initialized
     * the JadeConfiguration can be made.
     * @return the servletContext
     */
	public void setContext(ServletContext context) {
		this.context = context;
		setupJadeConfiguration();
	}
	private void setupJadeConfiguration() {
		configuration = new JadeConfiguration();
        configuration.setPrettyPrint(true);
        configuration.setCaching(false);
        String realPath = context.getRealPath("/");
    	realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        TemplateLoader loader = new FileTemplateLoader(realPath+"/templates/", "UTF-8");
        configuration.setTemplateLoader(loader);
	}		
	
	/**
	 * Return the JadeConfiguration, usually to the {@link JadeEngine}
	 * @return the configured JadeConfiguration.
	 */
	public JadeConfiguration getJadeConfiguration(){
		if (configuration==null)
			setupJadeConfiguration();
		return configuration;
	}

    
}
