package com.example.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.model.User;

@Stateless
@LocalBean
public class UserRepo {

	@PersistenceContext private EntityManager manager;
	
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
			System.out.println("exception: "+nre.getMessage());
			return null;
		}
	}
}
