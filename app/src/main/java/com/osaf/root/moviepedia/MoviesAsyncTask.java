package com.osaf.root.moviepedia;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.osaf.root.moviepedia.model.Movies;
import com.osaf.root.moviepedia.utilities.MoviepediaJsonUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MoviesAsyncTask extends AsyncTask<String, Void, ArrayList<Movies>> {


    private final String LOG_TAG = MoviesAsyncTask.class.getSimpleName();
    private OnTaskCompleted mListener;

    public MoviesAsyncTask(OnTaskCompleted listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Movies> doInBackground(String... params) {


        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {

            final String MOVIEDB_BASE_URL =
                    "https://api.themoviedb.org/3/movie/";
            final String APIKEY = "api_key";
            final String MY_API_KEY = "";


            Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APIKEY, MY_API_KEY)
                    .build();


            URL url = new URL(builtUri.toString());


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            urlConnection.setConnectTimeout(5000);


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {

                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            movieJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return MoviepediaJsonUtils.getParseMovieJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movies> movies) {
        super.onPostExecute(movies);
        mListener.onFetchMoviesTaskCompleted(movies);
    }
}


