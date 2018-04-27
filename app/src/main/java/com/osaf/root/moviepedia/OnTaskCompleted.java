package com.osaf.root.moviepedia;

import com.osaf.root.moviepedia.model.Movies;

public interface OnTaskCompleted {
    void onFetchMoviesTaskCompleted(Movies[] movies);
}
