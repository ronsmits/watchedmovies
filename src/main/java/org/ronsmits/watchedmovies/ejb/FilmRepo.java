package org.ronsmits.watchedmovies.ejb;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ronsmits.watchedmovies.model.Film;

/**
 * Session Bean
 */
@Stateless
@LocalBean
public class FilmRepo {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Default constructor.
     */
    public FilmRepo() {
	// TODO Auto-generated constructor stub
    }

    public List<Film> getList() {
	Query query = manager.createQuery("select m from Film m order by m.title asc");
	@SuppressWarnings("unchecked")
	List<Film> resultList = query.getResultList();
	return resultList;
    }

    public void save(Film film) {
	manager.merge(film);
    }

}
