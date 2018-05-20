package com.osaf.root.moviepedia.utilities;

import com.osaf.root.moviepedia.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviepediaJsonUtils {
    public static ArrayList<Movies> getParseMovieJson(String jsonMovies) throws JSONException {

        final String MDB_RESULT = "results";
        final String MDB_TITLE = "title";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_OVERVIEW = "overview";
        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185/";

        JSONObject movieJson = new JSONObject(jsonMovies);
        JSONArray movieArray = movieJson.getJSONArray(MDB_RESULT);

        ArrayList<Movies> movieArrayList = new ArrayList<>();

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movieObject = movieArray.getJSONObject(i);

            String title = movieObject.getString(MDB_TITLE);
            String release_date = movieObject.getString(MDB_RELEASE_DATE);
            String poster_path = movieObject.getString(MDB_POSTER_PATH);
            String vote_average = movieObject.getString(MDB_VOTE_AVERAGE);
            String overview = movieObject.getString(MDB_OVERVIEW);


            Movies movies = new Movies(title,
                    release_date,
                    IMAGE_BASE_URL + poster_path,
                    vote_average,
                    overview);

            movieArrayList.add(movies);
        }

        return movieArrayList;
    }
}
