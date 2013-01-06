package com.example.omdb;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ziplock.IO;

class RestClient {
	private static final String OMDBURL="http://www.omdbapi.com/";
	
	public String get(Object... path) throws IOException, URISyntaxException {
		return execute(new HttpGet(normalize(new URI(OMDBURL), path)));
	}

	public static String execute(HttpEntity entity,
			HttpEntityEnclosingRequestBase request) throws IOException {
		request.setEntity(entity);
		return execute(request);
	}

	public static String execute(HttpRequestBase request) throws IOException {
		System.out.println("executing " + request.toString());
		final HttpClient client = new DefaultHttpClient();
		final HttpResponse response = client.execute(request);

		final int code = response.getStatusLine().getStatusCode();
		final String message = asString(response);

		if (code < 200 || code >= 300) {
			throw new RuntimeException(response.getStatusLine().toString()
					+ " - " + message);
		}

		return message;
	}

	public static String asString(HttpResponse execute) throws IOException {
		if (execute == null || execute.getEntity() == null)
			return "";
		final InputStream in = execute.getEntity().getContent();
		try {
			return IO.slurp(in);
		} finally {
			in.close();
		}
	}
	
	public static URI normalize(URI uri, Object... path) {
        return URI.create(uri.toASCIIString() + "?" + join("&", path)).normalize();
    }

    public static String join(String delimiter, Object... collection) {
        if (collection.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : collection) {
            sb.append(obj).append(delimiter);
        }
        return sb.substring(0, sb.length() - delimiter.length());
    }
}