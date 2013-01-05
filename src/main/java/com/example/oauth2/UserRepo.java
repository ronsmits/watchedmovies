package com.example.oauth2;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.User;

@Stateless
@LocalBean
public class UserRepo {

	@PersistenceContext private EntityManager manager;
	Logger logger = LoggerFactory.getLogger(getClass());
	public void save(User user){
		manager.merge(user);
	}
	
	public User findUser(String username) {
		TypedQuery<User> query = manager.createNamedQuery("user.findUser", User.class);
		query.setParameter("username", username);
		try {
			User singleResult = query.getSingleResult();
			return singleResult;
		} catch (NoResultException nre){
			// a new user!
			logger.info("exception: "+nre.getMessage());
			return null;
		}
	}
}
