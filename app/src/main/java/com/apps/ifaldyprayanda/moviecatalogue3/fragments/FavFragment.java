package com.apps.ifaldyprayanda.moviecatalogue3.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.ifaldyprayanda.moviecatalogue3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;


    public FavFragment() {
        // Required empty public constructor
    }

    private class sliderAdapter extends FragmentPagerAdapter {
        String favMovie = getResources().getString(R.string.movie);
        String favTv = getResources().getString(R.string.tv_show);

        final String tabs[] = {favMovie, favTv};

        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FavMovieFragment();
                case 1:
                    return new FavTvShow();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fav, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

        tabLayout = view.findViewById(R.id.tab);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
