package com.example.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;

import com.example.ejb.Configuration;

import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.exceptions.JadeException;

public class JadeEngine {

	@EJB Configuration config;
	public String render(String view, Map<String, Object> map) throws JadeCompilerException, JadeException, IOException {
		map.put("context", config.getContext().getContextPath());
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, Object> next = iterator.next();
			System.out.println("key "+ next.getKey() + " value "+ next.getValue());
		}
		return config.getJadeConfiguration().renderTemplate(config.getJadeConfiguration().getTemplate(view), map);
	}
}
