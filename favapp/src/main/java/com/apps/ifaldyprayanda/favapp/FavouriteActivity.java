package com.apps.ifaldyprayanda.favapp;

import android.content.Context;
import android.database.Cursor;
import android.database.ContentObserver;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.CONTENT_URI;
import static com.apps.ifaldyprayanda.favapp.MappingHelper.mapCursorToArray;

public class FavouriteActivity extends AppCompatActivity implements LoadMovieFavCallback{

    private FavouriteMovieAdapter favouriteMovieAdapter;
    private DataObserver dataObserver;


    RecyclerView rvFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        rvFav = findViewById(R.id.rv_movie_fav);
         favouriteMovieAdapter = new FavouriteMovieAdapter(this);

         rvFav.setLayoutManager(new LinearLayoutManager(this));
         rvFav.setHasFixedSize(true);
         rvFav.setAdapter(favouriteMovieAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        dataObserver = new DataObserver(handler, this);

        getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);
        new getData(this, this).execute();

    }

    @Override
    public void postExecute(Cursor favList) {
        ArrayList<FavMovieItem> listFav = mapCursorToArray(favList);

        if (listFav.size() > 0)
        {
            favouriteMovieAdapter.setListMovieFav(listFav);
        }else
        {
            Toast.makeText(this, "Data Not there", Toast.LENGTH_SHORT).show();
            favouriteMovieAdapter.setListMovieFav(new ArrayList<FavMovieItem>());
        }
    }

    public static class getData extends AsyncTask<Void, Void, Cursor>
    {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieFavCallback> weakCallback;


        private getData(Context context, LoadMovieFavCallback callback)
        {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }

    }

    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (FavouriteActivity) context).execute();
        }
    }

}
