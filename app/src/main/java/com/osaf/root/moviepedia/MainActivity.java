package com.osaf.root.moviepedia;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.osaf.root.moviepedia.model.Movies;

public class MainActivity extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.movies_grid);

        if (savedInstanceState == null){
            getMoviesFromTMDb(getSortmethod());
        }else{
            Parcelable[] parcelables = savedInstanceState
                    .getParcelableArray(getString(R.string.parcel_movie));
            if (parcelables != null){
                int movieObj = parcelables.length;
                Movies[] movies = new Movies[movieObj];
                for (int i = 0; i < movieObj; i++) {
                    movies[i] = (Movies) parcelables[i];
                }
                gridView.setAdapter(new MovieAdapter(this,movies));
            }
        }

    }


    private void getMoviesFromTMDb(String sortMethod) {
            // Key needed to get data from TMDb
            //String apiKey = getString(R.string.key_themoviedb);

            // Listener for when AsyncTask is ready to update UI
            OnTaskCompleted taskCompleted = new OnTaskCompleted() {
                @Override
                public void onFetchMoviesTaskCompleted(Movies[] movies) {
                    gridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                }

            };
            moviesAsyncTask movietask = new moviesAsyncTask(taskCompleted);
            movietask.execute(sortMethod);
            // Execute task
            //movieAsyncTask movieTask = new movieAsyncTask(taskCompleted, apiKey);
           // movieTask.execute(R.string.tmdb_sort_pop_desc);
        }
        private String getSortmethod(){
        return String.valueOf(R.string.tmdb_sort_pop_desc);
        }
    }

