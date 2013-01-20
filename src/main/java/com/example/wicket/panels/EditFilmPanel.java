package com.example.wicket.panels;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.example.ejb.FilmRepo;
import com.example.model.Film;

public class EditFilmPanel extends Panel {
	private static final long serialVersionUID = 2285156439839094425L;

	@EJB private FilmRepo filmRepo;
	
	public EditFilmPanel(String id, final Film film) {
		super(id);
		Form<Film> form = new StatelessForm<Film>("form") {
			private static final long serialVersionUID = 4056790030965181777L;

			@Override
			public void onSubmit() {
				filmRepo.save(film);
			}
		};
		form.setDefaultModel(new CompoundPropertyModel<Film>(film));
		form.add(new TextField<Integer>("scoring"));
		form.add(new TextField<String>("watched"));
		add(form);
	}
	
}
