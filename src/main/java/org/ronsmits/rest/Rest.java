package org.ronsmits.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ronsmits.Movie;
import org.ronsmits.Repo;
import org.ronsmits.JadeEngine;

import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.exceptions.JadeException;

@Path(value="rest")
public class Rest {

	@EJB private Repo repo;
	@Inject private JadeEngine jadeEngine;
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() throws JadeCompilerException, JadeException, IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("movie", repo.getList());
		return jadeEngine.render("index", map);
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public List<Movie> getJSON() {
		return repo.getList();
	}
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postAMovie(
			MultivaluedMap<String, String> formparams) throws JadeCompilerException, JadeException, IOException{
			String title = formparams.getFirst("title");
			String watched = formparams.getFirst("date");
			String scoreStr = formparams.getFirst("score");
			int score;
			try {
				score = Integer.parseInt(scoreStr);
			} catch (NumberFormatException nfe) {
				throw new WebApplicationException(Status.NOT_ACCEPTABLE);
			}
			repo.save(new Movie(title, watched, score));
		return getHtml();
	}
	@GET
	@Path("add")
	@Produces(MediaType.TEXT_HTML) 
	public String getAddMovie() throws JadeCompilerException, JadeException, IOException{
		return jadeEngine.render("addmovie", new HashMap<String, Object>());
	}
}
