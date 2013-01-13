package com.example.ejb;

import java.util.Properties;

import com.example.model.Film;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

public class RepoTest {

	@EJB
	private FilmRepo filmRepo;

	@Before
	public void setup() throws Exception {
		Properties p = new Properties();
		p.put("movieDatabase", "new://Resource?type=DataSource");
		p.put("movieDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		p.put("movieDatabase.JdbcUrl","jdbc:hsqldb:mem:moviedb" + System.currentTimeMillis());
		EJBContainer container = EJBContainer.createEJBContainer(p);
		container.getContext().bind("inject", this);
	}

	@Test
	public void testGetList() throws Exception {
		Assert.assertEquals(0, filmRepo.getList().size());

		filmRepo.save(new Film("Blade Runner", "June 25, 1982", 10));

		Assert.assertEquals(1, filmRepo.getList().size());
	}
}
