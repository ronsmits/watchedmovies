package com.example.omdb;

public class OmdbMovieNotFoundException extends Exception {
	private static final long serialVersionUID = 3187323162778334666L;

	public OmdbMovieNotFoundException(String searchedFor) {
		super("movie "+searchedFor+" not found");
	}
}
