package com.osaf.root.moviepedia.utilities;

import com.osaf.root.moviepedia.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviepediaJsonUtils {
    public static Movies[] parseMovieJson(String jsonMovies) throws JSONException{
        final String M_RESULTS = "results";
        final String M_POSTERPATH = "poster_path";

        JSONObject movieJson = new JSONObject(jsonMovies);
        JSONArray resultsJson = movieJson.getJSONArray(M_RESULTS);

        //create arrray to store Movies object
        Movies[] movies = new Movies[resultsJson.length()];
        for (int i=0;i<resultsJson.length();i++){
            movies[i] = new Movies();
            JSONObject movieInfo = resultsJson.getJSONObject(i);
            movies[i].setPosterpath(movieInfo.getString(M_POSTERPATH));
        }
        //String posterJson = resultsJson.getString(M_POSTERPATH);

        //Movies movie = new Movies(posterJson);



        return movies;
    }
}
