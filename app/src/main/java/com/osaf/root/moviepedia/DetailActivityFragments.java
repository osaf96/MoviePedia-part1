package com.osaf.root.moviepedia;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.osaf.root.moviepedia.model.Movies;
import com.squareup.picasso.Picasso;

public class DetailActivityFragments extends Fragment {
    private static final String PARCEL_KEY = "movie_parcel";

    public DetailActivityFragments() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_fragment,
                container, false);
        Movies parceableExtra = getActivity().getIntent().getParcelableExtra(PARCEL_KEY);

        ImageView poster = view.findViewById(R.id.poster_IV);
        TextView title = view.findViewById(R.id.title_TV);
        TextView releaseDate = view.findViewById(R.id.relaesedate_TV);
        TextView vote = view.findViewById(R.id.vote_TV);
        TextView overView = view.findViewById(R.id.overview_TV);

        title.setText(parceableExtra.getTitle());
        releaseDate.setText(parceableExtra.getReleaseDate());
        vote.setText(parceableExtra.getVote());
        overView.setText(parceableExtra.getOverview());

        Picasso.with(view.getContext()).load(parceableExtra.getPosterPath())
                .into(poster);

        return view;
    }


}
