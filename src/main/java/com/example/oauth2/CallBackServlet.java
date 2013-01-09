package com.example.oauth2;

import java.io.IOException;
import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

@WebServlet(urlPatterns={"/callback.html", "/callback"})
public class CallBackServlet extends AbstractAuthorizationCodeCallbackServlet {
	private static final long serialVersionUID = 8150862402641087491L;
	private static final String GoogleUrl =  "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
	private String userid;

	@Inject private JPACredentialStore store;
	
	@Override
	  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	      throws ServletException, IOException {
		System.out.println("logged in ok");
		try {
			System.out.println("accesstoken "+ credential.getAccessToken());
			System.out.println("refreshtoken" + credential.getRefreshToken());
			loadGoogleInfo(credential);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cookie cookie = new Cookie("id", userid);
		cookie.setMaxAge(60);
	    resp.addCookie(cookie);

		resp.sendRedirect("index.html");
	  }
	
	private String loadGoogleInfo(Credential credential) throws JSONException, IOException {
		String url = GoogleUrl+credential.getAccessToken();
		WebClient client = WebClient.create(url);
		String string = client.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("string returned is "+ string);
		userid =(String) new JSONObject(string).get("id");
		store.store(userid, credential, new JSONObject(string));
		return userid;
	}
	@Override
	protected String getRedirectUri(HttpServletRequest arg0)
			throws ServletException, IOException {
		System.out.println("callback re-direct");
		return "http://localhost:8080/watchedmovies/callback";
	}

	@Override
	protected String getUserId(HttpServletRequest arg0)
			throws ServletException, IOException {
		System.out.println("callback getUserId");
		return null;
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
				.build();
	}

}
