package com.apps.ifaldyprayanda.moviecatalogue3.details;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.DatabaseHelper;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.CONTENT_URI;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.CHART;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.DATE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.OVERVIEW;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.POSTER;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.TITLE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.VOTE;

public class DetailMovieTopActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_TOP = "extra_movie_top";

    private TextView txTitle;
    private TextView txDate;
    private TextView txVote;
    private TextView txChart;
    private ImageView imgBackground;
    private ImageView imgPoster;
    private TextView txOverview;
    ProgressBar progressBarTop;
    FloatingActionButton floatingActionButton;

    private MovieHelper mMovieHelper;

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_top);

        imgBackground = findViewById(R.id.imgTopMovie);
        imgPoster = findViewById(R.id.imageView2);
        txTitle = findViewById(R.id.tx_movie);
        txDate = findViewById(R.id.tx_movie_date);
        txVote  = findViewById(R.id.vote);
        txChart = findViewById(R.id.chart);
        txOverview = findViewById(R.id.overview);
        progressBarTop = findViewById(R.id.progressbar_mtop);
        floatingActionButton = findViewById(R.id.fab_fav);

        progressBarTop.setVisibility(View.VISIBLE);

        mMovieHelper = new MovieHelper(this);
        mMovieHelper.open();

        final MovieTopData movieTopData = getIntent().getParcelableExtra(EXTRA_MOVIE_TOP);
        if (movieTopData != null)
        {
            progressBarTop.setVisibility(View.VISIBLE);
            txTitle.setText(movieTopData.getTitle());
            txDate.setText(movieTopData.getReleaseDate());
            String voteNumber = movieTopData.getVoteAverage().toString();
            String percent = "/10";
            String rating = voteNumber + percent;
            txVote.setText(rating);
            txChart.setText(String.valueOf(movieTopData.getPopularity()));
            txOverview.setText(movieTopData.getOverview());
            Glide.with(this)
                    .load(movieTopData.getPosterPath())
                    .apply(new RequestOptions().override(500, 219))
                    .into(imgBackground);
            Glide.with(this)
                    .load(movieTopData.getPosterPath())
                    .into(imgPoster);
            progressBarTop.setVisibility(View.GONE);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFav(movieTopData);
                }
            });
        }else
        {
            progressBarTop.setVisibility(View.VISIBLE);
        }
    }

    private void addFav(MovieTopData movieTopData)
    {
        int idMovie = movieTopData.getId();
        String title = movieTopData.getTitle();
        String releaseDate = movieTopData.getReleaseDate();
        String overview = movieTopData.getOverview();
        String imgPoster = movieTopData.getPosterPath();
        String vote = movieTopData.getVoteAverage().toString();
        String chart = movieTopData.getPopularity().toString();
        String txFav = getString(R.string.favmovie);
        String txFail = getString(R.string.dataFail);

        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, idMovie);
        contentValues.put(TITLE, title);
        contentValues.put(DATE, releaseDate);
        contentValues.put(OVERVIEW, overview);
        contentValues.put(POSTER, imgPoster);
        contentValues.put(VOTE, vote);
        contentValues.put(CHART, chart);

        Cursor cursor = mMovieHelper.queryByIdProvider(String.valueOf(idMovie));

        if(cursor.moveToNext())
        {
            Toast.makeText(getApplicationContext(), movieTopData.getTitle() + " " + txFail, Toast.LENGTH_SHORT).show();
            finish();
        }else
        {
            mMovieHelper.insertProvider(contentValues);
            Toast.makeText(getApplicationContext(), txFav + " : " + movieTopData.getTitle(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
