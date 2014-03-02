package org.ronsmits.watchedmovies.wicket.pages;

import java.util.Iterator;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import org.ronsmits.watchedmovies.ejb.FilmRepo;
import org.ronsmits.watchedmovies.model.Film;
import org.ronsmits.watchedmovies.wicket.bootstrap.paginator.BootstrapPaginator;
import org.ronsmits.watchedmovies.wicket.pages.template.AbstractPage;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;

@MountPath("/list.html")
public class ListPage extends AbstractPage {
	private static final long serialVersionUID = -2460208909526548826L;
	@EJB private FilmRepo filmRepo;
	
	public ListPage(){
		DataView<Film> dv = new DataView<Film>("rows", new ListDataProvider()) {
			private static final long serialVersionUID = -2460208909526548825L;

			@Override
			protected void populateItem(final Item<Film> item) {
				item.setDefaultModel(new CompoundPropertyModel<Film>(new Model<Film>(item.getModelObject())));
				item.add(new Link<Void>("link") {
					private static final long serialVersionUID = -7776322754369708763L;

					@Override
					public void onClick() {
						try {
							if (item.getModelObject().getImdbId()==null) {
								setResponsePage(new FindMovieInOmdb(item.getModel()));
							} else {
							setResponsePage(new ViewMoviePage(item.getModel()));
							}
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
					
				}.add(new Label("title", item.getModelObject().getTitle())));
				item.add(new Label("watched"));
				item.add(new Label("scoring"));
			}
		};
		dv.setItemsPerPage(10);
		add(new BootstrapPaginator("paging", dv));
		add(dv);
	}
	private class  ListDataProvider implements IDataProvider<Film>{
		private static final long serialVersionUID = 6516915081804073347L;

		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<? extends Film> iterator(long index, long count) {
			return filmRepo.getList().subList((int)index, (int)(index+count)).iterator();
		}

		@Override
		public IModel<Film> model(Film film) {
			return new Model<Film>(film);
		}

		@Override
		public long size() {
			return filmRepo.getList().size();
		}
		
	}
}
