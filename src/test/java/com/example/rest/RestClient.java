package com.example.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ziplock.IO;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class RestClient {

    public static String post(HttpEntity entity, URI url, Object... path) throws IOException {
        return execute(entity, new HttpPost(normalize(url, path)));
    }

    public static String put(URI url, HttpEntity entity, Object... path) throws IOException {
        return execute(entity, new HttpPut(normalize(url, path)));
    }

    public static String put(URI url, Object... path) throws IOException {
        return execute(new HttpPut(normalize(url, path)));
    }

    public static String get(URI url, Object... path) throws IOException {
        return execute(new HttpGet(normalize(url, path)));
    }

    public static String execute(HttpEntity entity, HttpEntityEnclosingRequestBase request) throws IOException {
        request.setEntity(entity);
        return execute(request);
    }

    public static String execute(HttpRequestBase request) throws IOException {

        final HttpClient client = new DefaultHttpClient();
        final HttpResponse response = client.execute(request);

        final int code = response.getStatusLine().getStatusCode();
        final String message = asString(response);

        if (code < 200 || code >= 300) {
            throw new RuntimeException(response.getStatusLine().toString() + " - " + message);
        }

        return message;
    }

    public static String asString(HttpResponse execute) throws IOException {
        if (execute == null || execute.getEntity() == null) return "";
        final InputStream in = execute.getEntity().getContent();
        try {
            return IO.slurp(in);
        } finally {
            in.close();
        }
    }

    public static URI normalize(URI uri, Object... path) {
        return URI.create(uri.toASCIIString() + "/" + join("/", path)).normalize();
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
