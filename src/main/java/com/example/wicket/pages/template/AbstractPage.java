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
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.ronsmits.slidenavigationpanel.SideNavigationPanel;
import org.ronsmits.slidenavigationpanel.SideNavigationPanel.Builder;

import com.example.model.User;
import com.example.oauth2.UserRepo;
import com.example.wicket.pages.AddFilm;
import com.example.wicket.pages.HomePage;
import com.example.wicket.pages.ListPage;
import com.example.wicket.panels.LoggedInPanel;
import com.example.wicket.panels.LoginPanel;

/**
 * 
 * @author Ron
 */
public abstract class AbstractPage extends WebPage {
	private static final long serialVersionUID = -5136152225467957580L;

	@EJB
	private UserRepo repo;
	protected User user;
	
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
		addLoginPanel();
	}

	private void setupMenu() {
		Builder builder = new Builder("navigation", getPage());
		builder = builder.addMenuItem("Home", HomePage.class).addMenuItem(
				"List", ListPage.class);
		if (checkLogin()) {
			builder.addMenuItem("Add", AddFilm.class);
		}
		add(new SideNavigationPanel(builder));

	}

	private void resetTimer(Cookie cookie) {
		cookie.setMaxAge(60 * 60);
		WebResponse response = (WebResponse) getRequestCycle()
				.getResponse();
		response.addCookie(cookie);
	}
	private void addLoginPanel() {
		if (!checkLogin()) { // did not find it
			add(new LoginPanel("login"));
		} else {
			add(new LoggedInPanel("login", user.getPictureurl(),
					user.getFirstName()));
			
		}

	}

	private boolean checkLogin() {
		WebRequest request = (WebRequest) getRequestCycle().getRequest();
		Cookie cookie = request.getCookie("id");
		if (cookie == null) { // did not find it
			return false;
		} else {
			user = repo.findUser(cookie.getValue());
			resetTimer(cookie);
			return true;
		}
	}
}
