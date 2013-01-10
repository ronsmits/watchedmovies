package com.example.wicket.pages;

import java.util.Iterator;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.example.ejb.MovieRepo;
import com.example.model.Movie;
import com.example.wicket.pages.template.AbstractPage;

@MountPath("/list.html")
public class ListPage extends AbstractPage {
	private static final long serialVersionUID = -2460208909526548826L;
	@EJB private MovieRepo movieRepo;
	
	public ListPage(){
		DataView<Movie> dv = new DataView<Movie>("rows", new ListDataProvider()) {
			private static final long serialVersionUID = -2460208909526548825L;

			@Override
			protected void populateItem(Item<Movie> item) {
				System.out.println("received "+ item.getModelObject().getTitle());
				item.setDefaultModel(new CompoundPropertyModel<Movie>(new Model<Movie>(item.getModelObject())));
				item.add(new Label("title"));
				item.add(new Label("watched"));
			}
		};
		dv.setItemsPerPage(10);
		add(new PagingNavigator("paging", dv));
		add(dv);
	}
	private class  ListDataProvider implements IDataProvider<Movie>{
		private static final long serialVersionUID = 6516915081804073347L;

		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<? extends Movie> iterator(long index, long count) {
			System.out.println("wanting " + index +" "+ count);
			return movieRepo.getList().subList((int)index, (int)(index+count)).iterator();
		}

		@Override
		public IModel<Movie> model(Movie movie) {
			System.out.println("working on " +movie.getTitle());
			return new Model<Movie>(movie);
		}

		@Override
		public long size() {
			System.out.println("count is "+ movieRepo.getList().size());
			return movieRepo.getList().size();
		}
		
	}
}
