package com.apps.ifaldyprayanda.moviecatalogue3;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.apps.ifaldyprayanda.moviecatalogue3.adapter.MovieTopAdapter;
import com.apps.ifaldyprayanda.moviecatalogue3.fragments.FavFragment;
import com.apps.ifaldyprayanda.moviecatalogue3.fragments.MovieFragment;
import com.apps.ifaldyprayanda.moviecatalogue3.fragments.TvShowFragment;
import com.apps.ifaldyprayanda.moviecatalogue3.notification.SettingActivity;

public class MainActivity extends AppCompatActivity {

    private MovieTopAdapter movieTopAdapter;
////    List<MovieTopData> movieTopData = new ArrayList<>();
    SearchView searchView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_tvshow:
                    fragment = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_fav:
                    fragment = new FavFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_movie);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.translate) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if (item.getItemId() == R.id.action_settings)
        {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle(getString(R.string.app_name));
        super.onBackPressed();
    }


}
