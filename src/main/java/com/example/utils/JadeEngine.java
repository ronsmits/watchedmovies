package com.example.utils;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;

import com.example.ejb.Configuration;

import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.exceptions.JadeException;

public class JadeEngine {

	@EJB Configuration config;
	public String render(String view, Map<String, Object> map) throws JadeCompilerException, JadeException, IOException {
		map.put("context", config.getContext().getContextPath());
		return config.getJadeConfiguration().renderTemplate(config.getJadeConfiguration().getTemplate(view), map);
	}
}
