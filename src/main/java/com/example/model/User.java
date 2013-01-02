package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import com.google.api.client.auth.oauth2.Credential;

@Entity(name="usertbl")
@NamedQueries({
	@NamedQuery(name="user.findAll", query="select u from usertbl u"),
	@NamedQuery(name="user.findUser", query="select u from usertbl u where u.username=:username")
})
public class User {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String pictureurl;

	@Transient
	private Credential credential;
	
	public User() {
		// default constructor.
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Credential getCredential() {
		return credential;
	}
	public void setCredential(Credential credential) {
		this.credential = credential;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureURL) {
		this.pictureurl = pictureURL;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
}
