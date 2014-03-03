package org.ronsmits.watchedmovies.wicket.pages;

import javax.ejb.EJB;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.ronsmits.omdbapi.Movie;
import org.ronsmits.omdbapi.Omdb;
import org.ronsmits.omdbapi.OmdbConnectionErrorException;
import org.ronsmits.omdbapi.OmdbMovieNotFoundException;
import org.ronsmits.omdbapi.OmdbSyntaxErrorException;
import org.ronsmits.watchedmovies.ejb.FilmRepo;
import org.ronsmits.watchedmovies.model.Film;
import org.ronsmits.watchedmovies.wicket.pages.template.AbstractPage;
import org.ronsmits.watchedmovies.wicket.panels.EditFilmPanel;
import org.ronsmits.watchedmovies.wicket.panels.ViewMoviePanel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("viewmovie")
public class ViewMoviePage extends AbstractPage {

    private static final long serialVersionUID = 2156959158360060743L;
    @EJB
    private FilmRepo filmRepo;

    public ViewMoviePage(final IModel<Film> model) throws OmdbMovieNotFoundException, OmdbConnectionErrorException, OmdbSyntaxErrorException {
	if (model.getObject().getImdbId() == null) {
	    setResponsePage(new FindMovieInOmdb(model));
	} else {
	    Movie movie = new Omdb().getById(model.getObject().getImdbId());
	    add(new EditFilmPanel("film", model.getObject()) {

		@Override
		public void save() {
		    filmRepo.save(model.getObject());
		}
	    });
	    add(new ViewMoviePanel("movie", Model.of(movie)));
	}
    }

}
