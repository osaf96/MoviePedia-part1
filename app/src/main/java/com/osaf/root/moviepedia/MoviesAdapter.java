package com.osaf.root.moviepedia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.osaf.root.moviepedia.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private final Context mContext;
    private final List<Movies> mMovies;
    private static final String PARCEL_KEY = "movie_parcel";

    public MoviesAdapter(Context context, ArrayList<Movies> moviesPostersList) {
        mContext = context;
        mMovies = moviesPostersList;
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public View mView;

        public MoviesViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.poster_image);
        }
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.poster_item, parent, false);
        return new MoviesViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, final int position) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Movies moviesParcel = mMovies.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(PARCEL_KEY, moviesParcel);

                context.startActivity(intent);
            }
        });
        Picasso.with(mContext).load(mMovies.get(position).getPosterPath())
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
