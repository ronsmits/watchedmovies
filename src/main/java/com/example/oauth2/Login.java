package com.example.oauth2;

import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class Login {
	private OAuthService service;
	private final String apiKey = "982732906864.apps.googleusercontent.com";
	private final String apiSecret = "ej9HWe7rjCWpSF_qILZ_UvvK";
	
	public Login () {
		service = new ServiceBuilder()
			.provider(GoogleApi.class)
			.apiKey(apiKey)
			.apiSecret(apiSecret)
			.scope("https://www.googleapis.com/auth/userinfo.profile")
			.callback("http://localhost:8080/movies/OauthCallBackServlet.html")
			.build();
		
		Token requestToken = service.getRequestToken();
		System.out.println("requestToken is " + requestToken);
		String authUrl = service.getAuthorizationUrl(requestToken);
		System.out.println(authUrl);
		Verifier verifier = new Verifier("cGyAQdODYuXtZ6UDUpHrT5vK");
		Token accessToken = service.getAccessToken(requestToken, verifier);
		System.out.println("access token is "+accessToken);
	}
}
