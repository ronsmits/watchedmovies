package com.example.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.ejb.Repo;
import com.example.model.Movie;
import com.example.model.User;
import com.example.oauth2.UserRepo;
import com.example.utils.JadeEngine;

import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.exceptions.JadeException;

/**
 * the router of the system. From here the separate URI's are guided to the right implementation.
 * @author ron
 *
 */
@Path(value="rest")
public class Rest {

	@EJB private Repo repo;
	@EJB private UserRepo userRepo;
	@Inject private JadeEngine jadeEngine;
	@Context HttpHeaders headers;
	@Context private UriInfo uriInfo;

	@GET
	
	@Produces(MediaType.TEXT_HTML)
	public String getHtmlWithId(@QueryParam("id") String id) throws JadeCompilerException, JadeException, IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		System.out.println("id is set to " +id);
		checkAndSetId(id, map);
		map.put("movie", repo.getList());
		return jadeEngine.render("list", map);		
	}

	private void checkAndSetId(String id, Map<String, Object> map) {
		if (id!=null) {
			map.put("id", id);
			User findUser = userRepo.findUser(id);
			map.put("picture", findUser.getPictureurl());
			map.put("name", findUser.getFirstName());
		}
	}
	
	@GET
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public List<Movie> getJSON() {
		return repo.getList();
	}
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postAMovie(
			MultivaluedMap<String, String> formparams) throws JadeCompilerException, JadeException, IOException, URISyntaxException{
			String title = formparams.getFirst("title");
			String watched = formparams.getFirst("date");
			String scoreStr = formparams.getFirst("score");
			String id = formparams.getFirst("id"); // this time the id is passed as a hidden input. I really need to find something else for this
			int score;
			try {
				score = Integer.parseInt(scoreStr);
			} catch (NumberFormatException nfe) {
				throw new WebApplicationException(Status.NOT_ACCEPTABLE);
			}
			repo.save(new Movie(title, watched, score));
			UriBuilder ub = uriInfo.getBaseUriBuilder();
			URL url = ub.path("view").path(title).queryParam("id", id).build().toURL();
			System.err.println(url);
		return Response.seeOther(url.toURI()).build();
	}
	
	@GET
	@Path("add")
	@Produces(MediaType.TEXT_HTML) 
	public String getAddMovie(@QueryParam("id") String id) throws JadeCompilerException, JadeException, IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		checkAndSetId(id, map);
		return jadeEngine.render("addmovie", map);
	}
}
