package com.apps.ifaldyprayanda.moviecatalogue3.details;

import android.content.ContentValues;
import android.database.Cursor;
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
import com.apps.ifaldyprayanda.moviecatalogue3.data.TvPopularData;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.TvHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_CHART;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_DATE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_OVERVIEW;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_POSTER;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_TITLE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_VOTE;


public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    private TextView txTitle;
    private TextView txDate;
    private TextView txVote;
    private TextView txChart;
    private ImageView imgBackground;
    private ImageView imgPoster;
    private TextView txOverview;
    ProgressBar progressBarTop;
    FloatingActionButton floatingActionButton;

    private TvHelper tHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        imgBackground = findViewById(R.id.imgTopMovie);
        imgPoster = findViewById(R.id.imageView2);
        txTitle = findViewById(R.id.tx_movie);
        txDate = findViewById(R.id.tx_movie_date);
        txVote = findViewById(R.id.vote);
        txChart = findViewById(R.id.chart);
        txOverview = findViewById(R.id.overview);
        progressBarTop = findViewById(R.id.progressbar_tv);
        floatingActionButton = findViewById(R.id.fab_tvfav);

        progressBarTop.setVisibility(View.VISIBLE);
        tHelper = new TvHelper(this);
        tHelper.open();

        final TvPopularData tvPopularData = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        if (tvPopularData != null) {
            progressBarTop.setVisibility(View.VISIBLE);
            txTitle.setText(tvPopularData.getName());
            txDate.setText(tvPopularData.getFirstAirDate());
            String voteNumber = tvPopularData.getVoteAverage().toString();
            String percent = "/10";
            String rating = voteNumber + percent;
            txVote.setText(rating);
            txChart.setText(String.valueOf(tvPopularData.getPopularity()));
            txOverview.setText(tvPopularData.getOverview());
            Glide.with(this)
                    .load(tvPopularData.getPosterPath())
                    .apply(new RequestOptions().override(500, 219))
                    .into(imgBackground);
            Glide.with(this)
                    .load(tvPopularData.getPosterPath())
                    .into(imgPoster);
            progressBarTop.setVisibility(View.GONE);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFav(tvPopularData);
                }
            });
        } else {
            progressBarTop.setVisibility(View.VISIBLE);
        }

    }

    private void addFav(TvPopularData tvPopularData)
    {
        int idTv = tvPopularData.getId();
        String title = tvPopularData.getName();
        String releaseDate = tvPopularData.getFirstAirDate();
        String overview = tvPopularData.getOverview();
        String imgPoster = tvPopularData.getPosterPath();
        String vote = tvPopularData.getVoteAverage().toString();
        String chart = tvPopularData.getPopularity().toString();
        String txFav = getString(R.string.tvfav);
        String txFail = getString(R.string.dataTv);

        ContentValues contentTvValues = new ContentValues();
        contentTvValues.put(_ID, idTv);
        contentTvValues.put(TV_TITLE, title);
        contentTvValues.put(TV_DATE, releaseDate);
        contentTvValues.put(TV_OVERVIEW, overview);
        contentTvValues.put(TV_POSTER, imgPoster);
        contentTvValues.put(TV_VOTE, vote);
        contentTvValues.put(TV_CHART, chart);

        Cursor cursor = tHelper.queryByIdProvider(String.valueOf(idTv));
        if (cursor.moveToNext())
        {
            Toast.makeText(getApplicationContext(), tvPopularData.getName() + " " + txFail, Toast.LENGTH_SHORT).show();
            finish();
        }else
        {
            tHelper.insertProvider(contentTvValues);
            Toast.makeText(DetailTvShowActivity.this, txFav + " : " + tvPopularData.getName(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
