package com.apps.ifaldyprayanda.moviecatalogue3.details;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.MainActivity;
import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieFavData;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.CONTENT_URI;

public class DetailMovieFavActivity extends AppCompatActivity {

    public static String EXTRA_MOVIE_FAV = "extra_movie_fav";

    private TextView txFavTitle;
    private TextView txFavDate;
    private TextView txFavVote;
    private TextView txFavChart;
    private ImageView imgBackgroundFav;
    private ImageView imgFavPoster;
    private TextView txFavOverview;

    private MovieFavData movieFavData;

    private MovieHelper movieHelper;

    ProgressBar progressBarFav;
    FloatingActionButton floatingActionButtonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_fav);

        txFavTitle = findViewById(R.id.tx_movieFav);
        txFavDate = findViewById(R.id.tx_movieFav_date);
        txFavVote = findViewById(R.id.voteMovieFav);
        txFavOverview = findViewById(R.id.overviewFav);
        imgFavPoster = findViewById(R.id.imageViewFav);
        imgBackgroundFav = findViewById(R.id.imgTopFavMovieMovie);
        txFavChart = findViewById(R.id.chartFav);
        progressBarFav = findViewById(R.id.progressbar_favMovie);

        floatingActionButtonDelete = findViewById(R.id.fab_del);

        progressBarFav.setVisibility(View.VISIBLE);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        final MovieFavData movieFavData = getIntent().getParcelableExtra(EXTRA_MOVIE_FAV);
        if (movieFavData != null) {
            txFavTitle.setText(movieFavData.getTitle());
            txFavDate.setText(movieFavData.getReleaseDate());
            String voteNumber = movieFavData.getVoteAverage().toString();
            String percent = "/10";
            txFavVote.setText(voteNumber + percent);
            txFavChart.setText(String.valueOf(movieFavData.getPopularity()));
            txFavOverview.setText(movieFavData.getOverview());
            Glide.with(this)
                    .load(movieFavData.getPosterPath())
                    .apply(new RequestOptions().override(500, 219))
                    .into(imgBackgroundFav);
            Glide.with(this)
                    .load(movieFavData.getPosterPath())
                    .into(imgFavPoster);

            progressBarFav.setVisibility(View.GONE);

            floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteAlertDialog(movieFavData);
                }
            });
        } else {
            progressBarFav.setVisibility(View.VISIBLE);
        }
    }

    private void showDeleteAlertDialog(final MovieFavData movieFavData) {
        String dialogMessage = getString(R.string.question);
        String dialogTitle = getString(R.string.dialog_title);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteMovie(movieFavData);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteMovie(MovieFavData movieFavData) {
        String id = String.valueOf(movieFavData.getId());
        int rowsDeleted = movieHelper.deleteProvider(id);
        if (rowsDeleted == 0) {
            Toast.makeText(this, movieFavData.getTitle() + " " + getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, movieFavData.getTitle() + " " + getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}
