package com.example.oauth2;

import java.io.IOException;
import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

@WebServlet("/login.html")
public class LoginServlet extends AbstractAuthorizationCodeServlet {

	@Inject private JPACredentialStore store;
	
	@Override
	protected String getRedirectUri(HttpServletRequest arg0)
			throws ServletException, IOException {
		System.out.println("getRedirectURI called");
		return "http://localhost:8080/movies/callback.html";
	}

	@Override
	protected String getUserId(HttpServletRequest arg0)
			throws ServletException, IOException {
		System.out.println("getUserId called");
		return "ron";
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException {
		System.out.println("initializeFlow called");
		return new GoogleAuthorizationCodeFlow.Builder(
				new NetHttpTransport(),
				new JacksonFactory(),
				"982732906864.apps.googleusercontent.com",
				"ej9HWe7rjCWpSF_qILZ_UvvK",
				Collections
						.singletonList("https://www.googleapis.com/auth/userinfo.profile"))
				.setCredentialStore(store).build();
	}

}
