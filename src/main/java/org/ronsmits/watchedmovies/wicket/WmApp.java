package org.ronsmits.watchedmovies.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IRequestCycleSettings.RenderStrategy;
import org.apache.wicket.settings.IResourceSettings;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;

import org.ronsmits.watchedmovies.utils.StripPackageResourceFinder;
import org.ronsmits.watchedmovies.utils.TomeeJndiNamingStrategy;
import org.ronsmits.watchedmovies.wicket.pages.HomePage;

public class WmApp extends WebApplication {

	@Override
	protected void init() {
		System.out.println("starting up");
		getRequestCycleSettings().setRenderStrategy(RenderStrategy.REDIRECT_TO_RENDER);
		getComponentInstantiationListeners().add(new JavaEEComponentInjector(this,new TomeeJndiNamingStrategy("watchedmovies")));
		getMarkupSettings().setStripWicketTags(true); // needed to make
														// bootstrap work
		setupResourceFinder();
		setupMounts();
	}

	private void setupMounts() {
		new AnnotatedMountScanner().scanPackage("org.ronsmits.watchedmovies.wicket.pages").mount(this);
		mountPage("index.html", HomePage.class);
	}
	private void setupResourceFinder() {
		IResourceSettings settings = getResourceSettings();
		settings.getResourceFinders().add(
				new StripPackageResourceFinder(getServletContext(),
						"org.ronsmits.watchedmovies.wicket"));
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
