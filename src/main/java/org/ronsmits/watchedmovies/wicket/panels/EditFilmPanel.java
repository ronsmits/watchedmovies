package org.ronsmits.watchedmovies.wicket.panels;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.ronsmits.watchedmovies.model.Film;

public abstract class EditFilmPanel extends Panel {

    private static final long serialVersionUID = 2285156439839094425L;

    public EditFilmPanel(String id, final Film film) {
	super(id, new CompoundPropertyModel<Film>(film));
	Form<Film> form = new StatelessForm<Film>("form") {
	    private static final long serialVersionUID = 4056790030965181777L;

	    @Override
	    public void onSubmit() {
		save();
	    }
	};
	form.setDefaultModel(new CompoundPropertyModel<Film>(film));
	form.add(new TextField<Integer>("scoring"));
	form.add(new TextField<String>("watched"));
	add(form);
    }

    abstract public void save();
}
