package com.osaf.root.moviepedia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.osaf.root.moviepedia.model.Movies;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter {

    private final Context mContext;
    private final Movies[]  mMovies;
    public MovieAdapter(Context context, Movies[] moviesPosters){
        mContext = context;
        mMovies = moviesPosters;
    }

    @Override
    public int getCount() {
        if (mMovies == null || mMovies.length == 0){
            return -1;
        }
        return mMovies.length;
    }

    @Override
    public Object getItem(int position) {
        if (mMovies == null || mMovies.length == 0){
            return null;
        }
        return mMovies[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView posterImage;
        if (convertView == null){
            posterImage = new ImageView(mContext);
            posterImage.setAdjustViewBounds(true);
        }else{
            posterImage = (ImageView) convertView;
        }

        Picasso.with(mContext).load(mMovies[position].getPosterPath()).into(posterImage);
        return posterImage;
    }
}
