/*
 * Copyright 2013 Ron.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wicket.pages.template;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.ronsmits.slidenavigationpanel.Page2;
import org.ronsmits.slidenavigationpanel.Page3;
import org.ronsmits.slidenavigationpanel.SideNavigationPanel;

import com.example.wicket.pages.HomePage;

/**
 *
 * @author Ron
 */
public abstract class AbstractPage extends WebPage {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5136152225467957580L;

	public AbstractPage() {
        super();
        setupMenu();
    }

    public AbstractPage(PageParameters parameters) {
        super(parameters);
        System.out.println("page " + getPage().toString());
        setupMenu();
    }

    private void setupMenu() {
        add(new SideNavigationPanel(new SideNavigationPanel.Builder("navigation", getPage())
                .addMenuItem("Home", HomePage.class)
                .addMenuItem("pagina 2", Page2.class)
                .addMenuItem("pagina 3", Page3.class)));
    }
}
