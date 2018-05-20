package com.osaf.root.moviepedia;

import com.osaf.root.moviepedia.model.Movies;

import java.util.ArrayList;

public interface OnTaskCompleted {
    void onFetchMoviesTaskCompleted(ArrayList<Movies> movies);
}
