package com.example.utils;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletContext;

import com.example.ejb.Configuration;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.exceptions.JadeException;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

public class JadeEngine {

	@EJB Configuration config;
	public String render(String view, Map<String, Object> map) throws JadeCompilerException, JadeException, IOException {
		map.put("context", config.getContext().getContextPath());
		//map.put("test", view);
		return config.getJadeConfiguration().renderTemplate(config.getJadeConfiguration().getTemplate(view), map);
	}
	
//	public JadeConfiguration getConfiguration() {
//		return configuration;
//	}
}
