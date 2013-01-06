package com.example.omdb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResult {
	private String title;
	private int year;
	private String imdbId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getImdbID() {
		return imdbId;
	}
	public void setImdbID(String imdbId) {
		this.imdbId = imdbId;
	}
}
