package com.example.omdb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


public class Omdb extends RestClient{

	public List<SearchResult> search(String string) throws OmdbMovieNotFoundException{
		try {
			String found = get("s="+URLEncoder.encode(string, "UTF-8"));
			System.out.println(found);
			String response= (String) new JSONObject(found).get("Response");
			if (response.equals("False"))
				throw new OmdbMovieNotFoundException(string);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
