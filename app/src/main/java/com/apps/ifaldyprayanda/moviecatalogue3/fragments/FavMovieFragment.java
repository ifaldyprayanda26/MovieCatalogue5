package com.apps.ifaldyprayanda.moviecatalogue3.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.adapter.FavouritAdapter;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieFavData;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {

    private Cursor mList;

    private Context mContext;

    private FavouritAdapter mFavAdapter;

    private RecyclerView rFavRv;
    private ProgressBar favProgressbar;

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieFavAsynctask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        rFavRv = view.findViewById(R.id.rv_movie_fav);
        rFavRv.setLayoutManager(new LinearLayoutManager(getContext()));
        rFavRv.setHasFixedSize(true);

        favProgressbar = view.findViewById(R.id.progressbar_fav);

        mFavAdapter = new FavouritAdapter(getContext());
        mFavAdapter.notifyDataSetChanged();
        rFavRv.setAdapter(mFavAdapter);

        new LoadMovieFavAsynctask().execute();
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class LoadMovieFavAsynctask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            favProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            favProgressbar.setVisibility(View.GONE);
            mList = notes;
            mFavAdapter.setListMovies(mList);
            mFavAdapter.notifyDataSetChanged();

        }
    }
}
