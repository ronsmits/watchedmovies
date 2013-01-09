package com.example.wicket;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;
import org.wicketstuff.javaee.naming.global.AppJndiNamingStrategy;
import org.wicketstuff.javaee.naming.global.ModuleJndiNamingStrategy;

import com.example.utils.StripPackageResourceFinder;
import com.example.utils.TomeeJndiNamingStrategy;
import com.example.wicket.pages.HomePage;

public class WmApp extends WebApplication {

	@Override
	protected void init() {
		System.out.println("starting up");
		getComponentInstantiationListeners().add(new JavaEEComponentInjector(this,new TomeeJndiNamingStrategy("watchedmovies")));
		getMarkupSettings().setStripWicketTags(true); // needed to make
														// bootstrap work
		setupResourceFinder();
		setupMounts();
	}

	private void setupMounts() {
		mountPage("index.html", HomePage.class);
	}
	private void setupResourceFinder() {
		IResourceSettings settings = getResourceSettings();
		settings.getResourceFinders().add(
				new StripPackageResourceFinder(getServletContext(),
						"com.example.wicket"));
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	/*
	private void setupCDI () {
		// Get BeanManager.
	    BeanManager bm;
	    try {
	        bm = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
	    } catch (NamingException e) {
	        throw new IllegalStateException("Unable to obtain CDI BeanManager", e);
	    }
	 
	    // Configure CDI, disabling Conversations as we aren't using them
	    new CdiConfiguration(bm).setPropagation(NONE).configure(this);
	}
*/
}
