package org.ronsmits.watchedmovies.oauth2;

import java.io.IOException;

import javax.ejb.EJB;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.ronsmits.watchedmovies.model.User;
import com.google.api.client.auth.oauth2.Credential;

public class JPACredentialStore  {

	@EJB private UserRepo userrepo;
	
	public void delete(String arg0, Credential credential) throws IOException {
		System.out.println("deleting: " + arg0);

	}

	public boolean load(String arg0, Credential credential) throws IOException {
		System.out.println("loading "+ arg0);
		return false;
	}

	public void store(String username, Credential credential, JSONObject object) throws IOException, JSONException {
		System.out.println("storing " + username);
		User user = userrepo.findUser(username);
		if (user == null)
			user = new User();
		user.setUsername(username);
		user.setFirstName((String) object.get("given_name"));
		user.setLastName((String) object.get("family_name"));
		user.setAccessToken(credential.getAccessToken());
		user.setRefreshToken(credential.getRefreshToken());
		if (object.has("picture"))
			user.setPictureurl((String) object.get("picture"));
		else 
			user.setPictureurl("http://placehold.it/103x103");

		userrepo.save(user);
	}

	public void update(String userid, JSONObject jsonObject) throws JSONException  {
		User user = userrepo.findUser(userid);
		if (user==null)
			return;
		user.setFirstName((String) jsonObject.get("given_name"));
		user.setLastName((String) jsonObject.get("family_name"));
		user.setPictureurl((String) jsonObject.get("picture"));
		userrepo.save(user);
	}

}
