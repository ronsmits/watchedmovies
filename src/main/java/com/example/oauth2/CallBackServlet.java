package com.example.oauth2;

import java.io.IOException;
import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ziplock.IO;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

@WebServlet("/callback.html")
public class CallBackServlet extends AbstractAuthorizationCodeCallbackServlet {

	private static final String GoogleUrl =  "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
	private String userid;
	@Inject private JPACredentialStore store;
	@Override
	  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	      throws ServletException, IOException {
		System.out.println("logged in ok");
		try {
			loadGoogleInfo(credential.getAccessToken());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    resp.sendRedirect("/movies?id="+userid);
	  }
	
	private void loadGoogleInfo(String accessToken) throws JSONException {
		String url = GoogleUrl+accessToken;
		WebClient client = WebClient.create(url);
		String string = client.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("string returned is "+ string);
		store.update(userid, new JSONObject(string));
	}
	@Override
	protected String getRedirectUri(HttpServletRequest arg0)
			throws ServletException, IOException {
		System.out.println("callback re-direct");
		return "http://localhost:8080/movies/callback.html";
	}

	@Override
	protected String getUserId(HttpServletRequest arg0)
			throws ServletException, IOException {
		System.out.println("callback getUserId");
		userid = "ron";
		return "ron";
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException {
		System.out.println("callback initialiseFlow");
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
