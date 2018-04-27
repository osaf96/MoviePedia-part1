package com.osaf.root.moviepedia.utilities;

import android.net.Uri;
import android.util.Log;

import com.osaf.root.moviepedia.model.Movies;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static String BASE_URL = "https://api.themoviedb.com/3/discover/movie?";
    private static final String format = "json";
    private static final String SORT_BUY = "sort_buy";

    //private static final String popularity = "popularit.desc";

    final static String API_KEY = "api_key=";
    final static String api_Key = "b2654ce2a237eb95193b8fe69cf1e7f5";

    public static URL buildUrl(String[] sortParams) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BUY, sortParams[0])
                .appendQueryParameter(API_KEY, api_Key).build();
        URL url = null;

        return new URL(builtUri.toString());
    }

    public static Movies[] getResponseFromHttpUrl(URL url) throws IOException {
        String moviesJsonString = null;
        BufferedReader reader = null;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            StringBuilder builder = new StringBuilder();

            if (in == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");

            }
            if (builder.length() == 0) {
                return null;
            }
            moviesJsonString = builder.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Error", "Error closing stream", e);
                }

            }

        }
        try {
            return MoviepediaJsonUtils.parseMovieJson(moviesJsonString);
        }catch (JSONException e){
            Log.e("Error","Closing Stream",e);
        }
        return null;
    }
}