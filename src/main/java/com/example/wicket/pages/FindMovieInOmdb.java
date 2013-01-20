package com.example.wicket.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.example.model.Film;
import com.example.wicket.pages.template.AbstractPage;
import com.omdbapi.Omdb;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;
import com.omdbapi.SearchResult;

public class FindMovieInOmdb extends AbstractPage {
	private static final long serialVersionUID = 6961505869120045913L;

	private IModel<Film> filmmodel;

	public FindMovieInOmdb() throws OmdbMovieNotFoundException, OmdbConnectionErrorException, OmdbSyntaxErrorException {
		new FindMovieInOmdb(filmmodel);
	}
	public FindMovieInOmdb(final IModel<Film> model) throws OmdbMovieNotFoundException, OmdbConnectionErrorException, OmdbSyntaxErrorException {
		this.filmmodel = model;
		setStatelessHint(false);
		List<SearchResult> list = new Omdb().search(model.getObject().getTitle());
		DataView<SearchResult> view = new DataView<SearchResult>("view", new ListDataProvider<SearchResult>(list)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<SearchResult> item) {
				item.setDefaultModel(new CompoundPropertyModel<SearchResult>(new Model<SearchResult>(item.getModelObject())));
				item.add(new Link<Void>("link") {
					private static final long serialVersionUID = -6447533194755925462L;

					@Override public void onClick(){
						try {
							setResponsePage(new AcceptMovie(model, item.getModel()));
						} catch (OmdbConnectionErrorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (OmdbSyntaxErrorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (OmdbMovieNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.add(new Label("title", item.getModelObject().getTitle())));
				item.add(new Label("year"));				
			}
		};
		add(view);
		
	}

}
