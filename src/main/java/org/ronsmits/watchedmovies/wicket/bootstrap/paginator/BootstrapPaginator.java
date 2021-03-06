package org.ronsmits.watchedmovies.wicket.bootstrap.paginator;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

public class BootstrapPaginator extends PagingNavigator {

    private static final long serialVersionUID = -6601654528238658380L;
    private final IPageable pageable;

    public BootstrapPaginator(final String id, final IPageable pageable) {
	super(id, pageable);
	this.pageable = pageable;
	if (pageable.getPageCount() == 1) {
	    this.setVisible(false);
	}

    }

    @Override
    public boolean isVisible() {
	return pageable.getPageCount() > 1;
    }

}
