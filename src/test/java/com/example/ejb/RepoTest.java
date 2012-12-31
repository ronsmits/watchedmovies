package com.example.ejb;

import java.util.Properties;

import com.example.model.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

public class RepoTest {

	@EJB
	private Repo repo;

	@Before
	public void setup() throws Exception {
		Properties p = new Properties();
		p.put("movieDatabase", "new://Resource?type=DataSource");
		p.put("movieDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		p.put("movieDatabase.JdbcUrl","jdbc:hsqldb:mem:moviedb" + System.currentTimeMillis());

		EJBContainer.createEJBContainer(p).getContext().bind("inject", this);
	}

	@Test
	public void testGetList() throws Exception {
		Assert.assertEquals(0, repo.getList().size());

		repo.save(new Movie("Blade Runner", "June 25, 1982", 10));

		Assert.assertEquals(1, repo.getList().size());
	}
}
