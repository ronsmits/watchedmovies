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

import javax.ejb.EJB;
import javax.servlet.http.Cookie;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.ronsmits.slidenavigationpanel.SideNavigationPanel;

import com.example.model.User;
import com.example.oauth2.UserRepo;
import com.example.wicket.pages.HomePage;
import com.example.wicket.panels.LoggedInPanel;
import com.example.wicket.panels.LoginPanel;

/**
 *
 * @author Ron
 */
public abstract class AbstractPage extends WebPage {

	@EJB private UserRepo repo;
    /**
	 * 
	 */
	private static final long serialVersionUID = -5136152225467957580L;

	public AbstractPage() {
        super();
        setup();
    }

    public AbstractPage(PageParameters parameters) {
        super(parameters);
        
        setup();
    }
    private void setup() {
    	setupMenu();
    	setupLogin();
    }
    private void setupMenu() {
        add(new SideNavigationPanel(new SideNavigationPanel.Builder("navigation", getPage())
                .addMenuItem("Home", HomePage.class)));
        
    }
    
    private void setupLogin() {
    	WebRequest request = (WebRequest) getRequestCycle().getRequest();
        Cookie cookie = request.getCookie("id");
        if (cookie==null) { // did not find it
        	add(new LoginPanel("login"));
        } else {
        	cookie.setMaxAge(60*60);
        	User user = repo.findUser(cookie.getValue());
        	add(new LoggedInPanel("login", user.getPictureurl(), user.getFirstName()));
        	WebResponse response = (WebResponse) getRequestCycle().getResponse();
        	response.addCookie(cookie);
        }
    	
    }
}
