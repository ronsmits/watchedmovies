package com.example.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.example.model.User;
import com.example.oauth2.UserRepo;
import com.example.utils.JadeEngine;
import com.omdbapi.Movie;
import com.omdbapi.Omdb;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;

import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.exceptions.JadeException;

@Path(value="view")
public class View {
	@Inject private JadeEngine jadeEngine;
	@EJB UserRepo userRepo;
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(value="{title}")
	public String getMovieInHtml(@PathParam("title") String title, @QueryParam("id") String id) throws JadeCompilerException, JadeException, IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		checkAndSetId(id, map);
		try {
			Movie byId = new Omdb().searchOneMovie(title);
			map.put("poster", byId.getPoster());
			map.put("title", byId.getTitle());
			map.put("director", byId.getDirector());
			map.put("plot", byId.getPlot());
			ArrayList<Movie> movies = new ArrayList<Movie>();
			movies.add(byId);
			map.put("movies", movies);
		} catch (OmdbConnectionErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OmdbSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OmdbMovieNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jadeEngine.render("viewmovie", map);
	}
	
	private void checkAndSetId(String id, Map<String, Object> map) {
		if (id!=null) {
			map.put("id", id);
			User findUser = userRepo.findUser(id);
			map.put("picture", findUser.getPictureurl());
			map.put("name", findUser.getFirstName());
		}
	}
}
