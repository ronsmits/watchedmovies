package org.ronsmits.watchedmovies.utils;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

public class StaticImage extends WebComponent {
	private static final long serialVersionUID = -8479849410860212908L;

	public StaticImage(String id, IModel<String> model) {
		super(id, model);
		
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
		tag.put("src", getDefaultModelObjectAsString());
	}

}
