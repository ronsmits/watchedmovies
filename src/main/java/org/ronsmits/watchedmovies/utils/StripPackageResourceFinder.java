package org.ronsmits.watchedmovies.utils;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;
import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StripPackageResourceFinder implements IResourceFinder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
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
	    logger.error("MalformedUrl {}", ex.getMessage());
	}
	return resourceStream;
    }

    private String stripPath(String pathname) {
	return pathname.replace(replacement, "");
    }
}
