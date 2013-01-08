package com.example.utils;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;

public class StripPackageResourceFinder implements IResourceFinder {
	private ServletContext context;
	private String replacement;

	public StripPackageResourceFinder(ServletContext context, String stripPath) {
		this.context = context;
		if (stripPath.contains(".")) {
			replacement = stripPath.replaceAll("\\.", "/");
		} else {
			replacement = stripPath;
		}
	}

	@Override
	public IResourceStream find(Class<?> clazz, String pathname) {
		IResourceStream resourceStream = null;
		String path = stripPath(pathname);
		try {
			URL url = context.getResource(path);
			if (url != null) {
				resourceStream = new UrlResourceStream(url);
			}
		} catch (MalformedURLException ex) {
			// dont do anything, file was not found, let another resourcefinder try it.
		}
		return resourceStream;
	}

	private String stripPath(String pathname) {
		return pathname.replace(replacement, "");
	}
}