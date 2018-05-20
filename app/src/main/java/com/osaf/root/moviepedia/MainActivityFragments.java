package com.osaf.root.moviepedia;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.osaf.root.moviepedia.model.Movies;

import java.util.ArrayList;

public class MainActivityFragments extends Fragment {
    private static final int COLUMN = 2;
    private RecyclerView mRecyclerView;
    SharedPreferences mSettings;

    private SharedPreferences.Editor mEditor;
    private static final String SHARED_KEY_SORT = "sort";
    private static final String POPULARITY = "popular";
    private static final String RATINGS = "top_rated";

    public static final String SAVE_LAST_UPDATE_ORDER = "save_last_update_order";
    private String mLastUpdateOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poster_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.rv_movies);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN));
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSettings.edit();
        mEditor.apply();
        mRecyclerView.setAdapter(new MoviesAdapter(getActivity(), new ArrayList<Movies>()));

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (needToUpdateUi()) {
            updateUi();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_LAST_UPDATE_ORDER, mLastUpdateOrder);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mLastUpdateOrder = savedInstanceState.getString(SAVE_LAST_UPDATE_ORDER);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        updateUi();
    }


    //   OnCreateOptionMenues  will be here
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.poster_fragment, menu);
        Drawable drawable = menu.findItem(R.id.icon).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }


    //  OnOptionitemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.poularity:
                mEditor.putString(SHARED_KEY_SORT, POPULARITY);
                mEditor.apply();
                updateUi();
                item.setChecked(true);
                return true;
            case R.id.top_rated:
                mEditor.putString(SHARED_KEY_SORT, RATINGS);
                mEditor.apply();
                updateUi();
                item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        String sortBy = mSettings.getString(SHARED_KEY_SORT, POPULARITY);
        if (sortBy.equals(POPULARITY)) {
            menu.findItem(R.id.poularity).setChecked(true);
        } else {
            menu.findItem(R.id.top_rated).setChecked(true);
        }
    }

    private void updateUi() {
        if (isNetworkAvailable()) {
            OnTaskCompleted taskCompleted = new OnTaskCompleted() {
                @Override
                public void onFetchMoviesTaskCompleted(ArrayList<Movies> movies) {
                    mRecyclerView.setAdapter(new MoviesAdapter(getActivity(), movies));
                }
            };
            MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(taskCompleted);
            mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());


            String sortBy = mSettings.getString(SHARED_KEY_SORT, POPULARITY);
            mLastUpdateOrder = sortBy;
            moviesAsyncTask.execute(sortBy);
        } else {
            Toast.makeText(this.getActivity().getApplicationContext(), "Need Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    private boolean needToUpdateUi() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!mLastUpdateOrder.equals(prefs.getString(SHARED_KEY_SORT, POPULARITY))) {
            return true;
        } else {
            return false;
        }
    }


    //Based on a stackoverflow snippet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


