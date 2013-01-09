package com.example.wicket.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.example.utils.StaticImage;

public class LoggedInPanel extends Panel {
	private static final long serialVersionUID = -4680154381802717428L;

	public LoggedInPanel(String id, String pictureURL, String name) {
		super(id);
		add(new Label("name", Model.of(name)));
		add(new StaticImage("photo", new Model<String>(pictureURL)));
	}

}
