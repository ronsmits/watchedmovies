package com.example.wicket.panels;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import com.example.utils.StaticImage;
import com.omdbapi.Movie;

public class ViewMoviePanel extends Panel {
	private static final long serialVersionUID = 6932113626692345986L;

	public ViewMoviePanel(String id, Model<Movie> model) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<Movie>(model));
		add(new ExternalLink("titlelink", "http://www.imdb.com/title/"+model.getObject().getImdbId(), model.getObject().getTitle()));
		add(new Label("director"));
		add(listView("actors", model.getObject().getActors()));
		add(listView("writers", model.getObject().getWriters()));
		add(new Label("imdbRating", Model.of(model.getObject().getImdbRating())));
		add(new Label("year"));
		add(new Label("runtime"));
		add(new Label("plot"));
		add(new StaticImage("poster", Model.of(model.getObject().getPoster())));
	}
	
	private RepeatingView listView(String id, List<String> list) {
		RepeatingView view = new RepeatingView(id);
		for (String element : list){
			view.add(new Label(view.newChildId(), Model.of(element)));
		}
		return view;
		

	}
}
