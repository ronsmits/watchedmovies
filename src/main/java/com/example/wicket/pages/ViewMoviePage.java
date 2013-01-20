package com.example.wicket.pages;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.example.model.Film;
import com.example.wicket.pages.template.AbstractPage;
import com.example.wicket.panels.EditFilmPanel;
import com.example.wicket.panels.ViewMoviePanel;
import com.omdbapi.Movie;
import com.omdbapi.Omdb;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;

@MountPath("viewmovie")
public class ViewMoviePage extends AbstractPage {
	private static final long serialVersionUID = 2156959158360060743L;

	public ViewMoviePage(IModel<Film> model) throws OmdbMovieNotFoundException, OmdbConnectionErrorException, OmdbSyntaxErrorException {
		System.out.println(model.getObject());
		if (model.getObject().getImdbId()==null) {
			setResponsePage(new FindMovieInOmdb(model));
		} else {
			Movie movie = new Omdb().getById(model.getObject().getImdbId());
			add(new EditFilmPanel("film", model.getObject()));
			add(new ViewMoviePanel("movie", Model.of(movie)));
		}
	}

}
