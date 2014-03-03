package org.ronsmits.watchedmovies.wicket.pages;

import java.util.Date;
import javax.ejb.EJB;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.ronsmits.omdbapi.Movie;
import org.ronsmits.omdbapi.Omdb;
import org.ronsmits.omdbapi.OmdbConnectionErrorException;
import org.ronsmits.omdbapi.OmdbMovieNotFoundException;
import org.ronsmits.omdbapi.OmdbSyntaxErrorException;
import org.ronsmits.omdbapi.SearchResult;
import org.ronsmits.watchedmovies.ejb.FilmRepo;
import org.ronsmits.watchedmovies.model.Film;
import org.ronsmits.watchedmovies.wicket.pages.template.AbstractPage;
import org.ronsmits.watchedmovies.wicket.panels.ViewMoviePanel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("acceptmovie")
public class AcceptMovie extends AbstractPage {

    private static final long serialVersionUID = -5525873111302184401L;

    private Omdb omdb = new Omdb();
    IModel<Film> model;
    @EJB
    private FilmRepo filmRepo;

    public AcceptMovie(IModel<SearchResult> searchResultModel) throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
	setup(false, searchResultModel);
    }

    public AcceptMovie(IModel<Film> filmModel, IModel<SearchResult> searchResultModel)
	    throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
	this.model = filmModel;
	setup(true, searchResultModel);
    }

    private void setup(final boolean visible, final IModel<SearchResult> searchResultModel)
	    throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
	Movie movie = omdb.fullPlot().getById(searchResultModel.getObject().getImdbID());
	add(new ViewMoviePanel("moviepanel", new Model<Movie>(movie)));
	Form<Film> form = new Form<Film>("acceptForm", model);
	form.add(new Button("accept") {
	    private static final long serialVersionUID = -8833376680081629756L;

	    public boolean isVisible() {
		return visible;
	    }

	    public void onSubmit() {
		model.getObject().setTitle(searchResultModel.getObject().getTitle());
		model.getObject().setImdbId(searchResultModel.getObject().getImdbID());
		model.getObject().setAddedBy(user);
		filmRepo.save(model.getObject());
		setResponsePage(ListPage.class);
	    }
	});
	form.add(new TextField<Date>("watched"));
	form.add(new TextField<Integer>("scoring"));
	add(form);

    }

}
