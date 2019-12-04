package com.apps.ifaldyprayanda.moviecatalogue3.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.adapter.MovieTopAdapter;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.apps.ifaldyprayanda.moviecatalogue3.details.DetailMovieTopActivity;
import com.apps.ifaldyprayanda.moviecatalogue3.viewmodel.MovieTopViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieTopAdapter movieTopAdapter;
    private RecyclerView rvMovieTop;

    private SearchView searchView;
    private ProgressBar progressBarTop;

    private ArrayList<MovieTopData> movieTopData = new ArrayList<>();

    private MovieTopViewModel movieTopViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovieTop = view.findViewById(R.id.rv_movie_top);
        progressBarTop = view.findViewById(R.id.progressbar_top);
        progressBarTop.setVisibility(View.VISIBLE);

        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.onActionViewExpanded();

        movieTopViewModel = ViewModelProviders.of(this).get(MovieTopViewModel.class);
        movieTopViewModel.getListMovieTop().observe(this, getMovieTop);

        movieTopAdapter = new MovieTopAdapter(movieTopData);
        movieTopAdapter.notifyDataSetChanged();

        rvMovieTop.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        rvMovieTop.setAdapter(movieTopAdapter);

        movieTopViewModel.setListMovieTop(getContext());


        movieTopAdapter.setOnItemCallback(new MovieTopAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieTopData movieTop) {
                clickSelectedMovie(movieTop);
            }
        });

        progressBarTop.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty())
                {
                    movieTopViewModel.searchMovie(getContext(), newText);
                    progressBarTop.setVisibility(View.GONE);
                }
                if (newText.isEmpty())
                {
                    movieTopViewModel.setListMovieTop(getContext());
                    progressBarTop.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    private final Observer<ArrayList<MovieTopData>> getMovieTop = new Observer<ArrayList<MovieTopData>>() {
        @Override
        public void onChanged(ArrayList<MovieTopData> movieTopData) {
            progressBarTop.setVisibility(View.VISIBLE);
            if (movieTopData != null) {
                movieTopAdapter.setMovieTopData(movieTopData);
                progressBarTop.setVisibility(View.GONE);
            }else
            {
                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void clickSelectedMovie(MovieTopData movieTopData) {
        String txClick = getString(R.string.tx_movie_click);
        Toast.makeText(getContext(), txClick + movieTopData.getTitle(), Toast.LENGTH_SHORT).show();
        Intent tIntent = new Intent(getActivity(), DetailMovieTopActivity.class);
        tIntent.putExtra(DetailMovieTopActivity.EXTRA_MOVIE_TOP, movieTopData);
        startActivity(tIntent);
    }

}
