package org.ronsmits.rest;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.servlet.ServletContext;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * Session Bean implementation class Configuration
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
	public ServletContext getContext() {
		return context;
	}
	public void setContext(ServletContext context) {
		System.out.println("context realpath "+context.getRealPath("/"));
		this.context = context;
		setupJadeConfiguration();
	}
	private void setupJadeConfiguration() {
		configuration = new JadeConfiguration();
        configuration.setPrettyPrint(true);
        String realPath = context.getRealPath("/");
    	realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        TemplateLoader loader = new FileTemplateLoader(realPath+"/", "UTF-8");
        configuration.setTemplateLoader(loader);
	}		
	
	public JadeConfiguration getJadeConfiguration(){
		if (configuration==null)
			setupJadeConfiguration();
		return configuration;
	}

    
}
