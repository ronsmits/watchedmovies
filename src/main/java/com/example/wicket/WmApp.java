package com.example.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;

import com.example.utils.StripPackageResourceFinder;
import com.example.wicket.pages.HomePage;

public class WmApp extends WebApplication {

	@Override
	protected void init() {
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

}
