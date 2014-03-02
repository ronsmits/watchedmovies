package org.ronsmits.watchedmovies.utils;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

/**
 * Servlet Filter implementation class StartFilter
 */
@WebFilter(
		urlPatterns = {"/*"},
		initParams = { 
				@WebInitParam(name = "applicationClassName", value = "org.ronsmits.watchedmovies.wicket.WmApp"),
				@WebInitParam(name = "filterMappingUrlPattern", value="/*")
		})
public class StartFilter extends WicketFilter implements Filter {
       
    /**
     * @see WicketFilter#WicketFilter()
     */
    public StartFilter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see WicketFilter#WicketFilter(WebApplication)
     */
    public StartFilter(WebApplication application) {
        super(application);
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
