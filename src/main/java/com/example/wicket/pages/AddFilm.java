package com.example.wicket.pages;

import java.util.Date;

import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import com.example.model.Film;
import com.example.wicket.pages.template.AbstractPage;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;

public class AddFilm extends AbstractPage {
	private static final long serialVersionUID = -7119379030851976047L;

	public AddFilm(){
		Film film = new Film();
		StatelessForm<Film> form = new StatelessForm<Film>("form", new CompoundPropertyModel<Film>(film)){
			private static final long serialVersionUID = 1148355668651725390L;

			@Override
			public void onSubmit(){
				try {
					setResponsePage(new FindMovieInOmdb(getModel()));
				} catch (OmdbMovieNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OmdbConnectionErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OmdbSyntaxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		form.add(new TextField<String>("title"));
		form.add(new TextField<Date>("watched"));
		form.add(new TextField<Integer>("scoring"));
		add(form);
	}
}
