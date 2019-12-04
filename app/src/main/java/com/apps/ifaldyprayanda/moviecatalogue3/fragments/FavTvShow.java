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
import com.apps.ifaldyprayanda.moviecatalogue3.adapter.FavouriteTvAdapter;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.TvHelper;

import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvShow extends Fragment {
    private Cursor tList;

    private Context mContext;

    private FavouriteTvAdapter tvFavAdapter;

    private RecyclerView tvFavRv;
    private ProgressBar favTvProgressbar;


    public FavTvShow() {
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

    private void showNotifTv() {
        Snackbar.make(tvFavRv, "Tidak Ada Data Tv", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadTvFavAsyncTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
        tvFavRv = view.findViewById(R.id.rv_tv_fav);
        tvFavRv.setLayoutManager(new LinearLayoutManager(getContext()));
        tvFavRv.setHasFixedSize(true);

        favTvProgressbar = view.findViewById(R.id.progressbar_favTv);

        tvFavAdapter = new FavouriteTvAdapter(getContext());
        tvFavAdapter.setListTv(tList);
        tvFavRv.setAdapter(tvFavAdapter);

        new LoadTvFavAsyncTask().execute();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class LoadTvFavAsyncTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            favTvProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor tNotes) {
            super.onPostExecute(tNotes);
            favTvProgressbar.setVisibility(View.GONE);

            tList = tNotes;
            tvFavAdapter.setListTv(tList);
            tvFavAdapter.notifyDataSetChanged();

        }
    }
}
