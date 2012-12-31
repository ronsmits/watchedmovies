package com.example.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceUnit;

import com.example.model.Movie;

/**
 * Session Bean implementation class Repo
 */
@Stateless
@LocalBean
public class Repo {
	private static List<Movie> movies = new ArrayList<Movie>();
	

    /**
     * Default constructor. 
     */
    public Repo() {
        // TODO Auto-generated constructor stub
    }
    
    public List<Movie> getList() {
    	return movies;
    }
    
    public void save(Movie movie) {
    	movies.add(movie);
    }

}
