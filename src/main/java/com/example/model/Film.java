package com.example.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Film implements Serializable{
	private static final long serialVersionUID = 2956979688081192919L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String title;
	private String watched;
	private int scoring;
	private String imdbId;
	@OneToOne
	private User addedBy;
	

	public Film() {
		// standard constructor
	}
	
	public Film (String title, String watched, int scoring, User user) {
		this.title = title;
		this.watched = watched;
		this.scoring = scoring;
		this.addedBy = user;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWatched() {
		return watched;
	}
	public void setWatched(String watched) {
		this.watched = watched;
	}
	public int getScoring() {
		return scoring;
	}
	public void setScoring(int scoring) {
		this.scoring = scoring;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	
	public void setAddedBy (User user) {
		this.addedBy = user;
	}
	
	public User getAddedBy() {
		return addedBy;
	}
}
