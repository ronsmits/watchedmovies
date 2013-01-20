package com.example.wicket.pages;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.example.ejb.FilmRepo;
import com.example.model.Film;
import com.example.wicket.pages.template.AbstractPage;
import com.example.wicket.panels.ViewMoviePanel;
import com.omdbapi.Movie;
import com.omdbapi.Omdb;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;
import com.omdbapi.SearchResult;

@MountPath("acceptmovie")
public class AcceptMovie extends AbstractPage {
	private static final long serialVersionUID = -5525873111302184401L;

	private Omdb omdb = new Omdb();
	IModel<Film> model;
	@EJB private FilmRepo filmRepo;
	
	public AcceptMovie(IModel<SearchResult> searchResultModel) throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
		setup(false, searchResultModel);
	}

	public AcceptMovie(IModel<Film> filmModel, IModel<SearchResult> searchResultModel) throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
		this.model = filmModel;
		setup(true, searchResultModel);
	}
	private void setup(final boolean visible, final IModel<SearchResult> searchResultModel) throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException{
		Movie movie = omdb.fullPlot().getById(searchResultModel.getObject().getImdbID());
		add(new ViewMoviePanel("moviepanel", new Model<Movie>(movie)));
		Form<Void> form = new Form<Void>("acceptForm");
		form.add(new Button("accept"){
			private static final long serialVersionUID = -8833376680081629756L;

			public boolean isVisible(){
				return visible;
			}
			public void onSubmit(){
				model.getObject().setTitle(searchResultModel.getObject().getTitle());
				model.getObject().setImdbId(searchResultModel.getObject().getImdbID());
				model.getObject().setAddedBy(user);
				filmRepo.save(model.getObject());
				setResponsePage(ListPage.class);
			}
		});
		add(form);

	}
	
}
