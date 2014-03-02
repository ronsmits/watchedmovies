package org.ronsmits.watchedmovies.ejb;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.ronsmits.watchedmovies.model.User;
import org.ronsmits.watchedmovies.oauth2.UserRepo;

public class UserRepoTest {

	@EJB private UserRepo userRepo;
	private EJBContainer container;
	@Before
	public void setup() throws Exception {
		Properties p = new Properties();
		p.put("movieDatabase", "new://Resource?type=DataSource");
		p.put("movieDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		p.put("movieDatabase.JdbcUrl","jdbc:hsqldb:mem:moviedb" + System.currentTimeMillis());
		container = EJBContainer.createEJBContainer(p);
		container.getContext().bind("inject", this);
	}
	
	@Test
	public void testCannotFindUser() {
		User user = userRepo.findUser("cannot be found");
		assertNull(user);
	}
	
	@Test
	public void testSaveUser() {
		User user = new User();
		user.setUsername("username");
		user.setFirstName("ron");
		user.setLastName("lastname");
		userRepo.save(user);
		User user2 = userRepo.findUser("username");
		assertEquals("username", user2.getUsername());
	}
	
	@After
	public void shutdown() {
		container.close();
	}
}
