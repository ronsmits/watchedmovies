package org.ronsmits.watchedmovies.wicket.panels;

import javax.servlet.http.Cookie;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import org.ronsmits.watchedmovies.utils.StaticImage;

public class LoggedInPanel extends Panel {
	private static final long serialVersionUID = -4680154381802717428L;

	public LoggedInPanel(String id, String pictureURL, String name) {
		super(id);
		add(new Label("name", Model.of(name)));
		add(new StaticImage("photo", new Model<String>(pictureURL)));
		Form<Void> form = new StatelessForm<Void>("logoutform"){
			private static final long serialVersionUID = 513129937918256386L;

			@Override
			public void onSubmit() {
				WebRequest request = (WebRequest) getRequestCycle().getRequest();
				Cookie cookie = request.getCookie("id");
				WebResponse response = (WebResponse) getRequestCycle()
						.getResponse();
				response.clearCookie(cookie);
			}
		};
		add(form);
	}

}
