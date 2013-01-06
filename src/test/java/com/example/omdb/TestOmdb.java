package com.example.omdb;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestOmdb {

	@Test
	public void testForStarWars() throws OmdbMovieNotFoundException {
		List<SearchResult> results = new Omdb().search("bestaat niet");
		assertEquals(4, results.size());
	}

	@Test(expected=OmdbMovieNotFoundException.class)
	public void testForDoesNotExist() throws OmdbMovieNotFoundException {
		new Omdb().search("doesnot exist");
	}
}
