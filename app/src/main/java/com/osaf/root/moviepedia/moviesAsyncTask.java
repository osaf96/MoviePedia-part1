package com.osaf.root.moviepedia;

import android.os.AsyncTask;
import android.util.Log;

import com.osaf.root.moviepedia.model.Movies;
import com.osaf.root.moviepedia.utilities.MoviepediaJsonUtils;
import com.osaf.root.moviepedia.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class moviesAsyncTask extends AsyncTask<String,Void,Movies[]> {


    private final OnTaskCompleted mlistener;
    public moviesAsyncTask(OnTaskCompleted listener){
        super();
        mlistener = listener;
    }

    @Override
    protected Movies[] doInBackground(String... strings) {
        String moviesJsonString = null;
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = null;
            try {
                url = NetworkUtils.buildUrl(strings);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                 urlConnection =(HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                urlConnection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            try {
                urlConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            try {
                in = urlConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    protected void onPostExecute(Movies[] movies) {
        super.onPostExecute(movies);

        mlistener.onFetchMoviesTaskCompleted(movies);
    }
}


