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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.adapter.TvPopularAdapter;
import com.apps.ifaldyprayanda.moviecatalogue3.data.TvPopularData;
import com.apps.ifaldyprayanda.moviecatalogue3.details.DetailTvShowActivity;
import com.apps.ifaldyprayanda.moviecatalogue3.viewmodel.TvPopularViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TvPopularAdapter tvPopularAdapter;
    private RecyclerView rvTvPopular;
    private ProgressBar progressBarPopular;

    private SearchView searchView;

    private ArrayList<TvPopularData> tvPopularData = new ArrayList<>();

    private TvPopularViewModel tvPopularViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvPopular = view.findViewById(R.id.rv_tv_pop);
        progressBarPopular = view.findViewById(R.id.progressbar_pop);
        progressBarPopular.setVisibility(View.VISIBLE);

        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.onActionViewExpanded();

        tvPopularViewModel = ViewModelProviders.of(this).get(TvPopularViewModel.class);
        tvPopularViewModel.getTvPopular().observe(this, getTvPopular);

        tvPopularAdapter = new TvPopularAdapter(tvPopularData);
        tvPopularAdapter.notifyDataSetChanged();

        rvTvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        rvTvPopular.setAdapter(tvPopularAdapter);

        tvPopularViewModel.setTvPopular(getContext());

        tvPopularAdapter.setOnItemClickCallback(new TvPopularAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvPopularData tvPop) {
                clickSelectedTvPop(tvPop);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty())
                {
                    progressBarPopular.setVisibility(View.VISIBLE);
                    tvPopularViewModel.searchTv(getContext(), newText);
                    progressBarPopular.setVisibility(View.GONE);
                }
                if (newText.isEmpty())
                {
                    progressBarPopular.setVisibility(View.VISIBLE);
                    tvPopularViewModel.setTvPopular(getContext());
                    progressBarPopular.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }

    private final Observer<ArrayList<TvPopularData>> getTvPopular = new Observer<ArrayList<TvPopularData>>() {
        @Override
        public void onChanged(ArrayList<TvPopularData> tvShowPopularData) {
            if (tvShowPopularData != null) {
                tvPopularAdapter.setTvShowPopularData(tvShowPopularData);
                progressBarPopular.setVisibility(View.GONE);
            }
        }
    };

    private void clickSelectedTvPop(TvPopularData tvPop) {
        String txClick = getString(R.string.tx_tv_click);
        Toast.makeText(getActivity(), txClick + tvPop.getName(), Toast.LENGTH_SHORT).show();
        Intent tvIntent = new Intent(getActivity(), DetailTvShowActivity.class);
        tvIntent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvPop);
        startActivity(tvIntent);
    }
}

