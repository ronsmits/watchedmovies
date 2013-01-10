package com.example.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.model.Movie;

/**
 * Session Bean 
 */
@Stateless
@LocalBean
public class MovieRepo {
	
	@PersistenceContext private EntityManager manager;
    /**
     * Default constructor. 
     */
    public MovieRepo() {
        // TODO Auto-generated constructor stub
    }
    
    public List<Movie> getList() {
    	Query query = manager.createQuery("select m from Movie m order by m.watched desc");
    	@SuppressWarnings("unchecked")
		List<Movie> resultList = query.getResultList();
    	return resultList;
    }
    
    public void save(Movie movie) {
    	manager.merge(movie);
    }

}
