package com.apps.ifaldyprayanda.moviecatalogue3.details;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.TvFavData;
import com.apps.ifaldyprayanda.moviecatalogue3.data.TvPopularData;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.TvHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailTvFavActivity extends AppCompatActivity {

    public static String EXTRA_TV_FAV = "extra_tv_fav";

    private TextView txFavTitle;
    private TextView txFavDate;
    private TextView txFavVote;
    private TextView txFavChart;
    private ImageView imgBackgroundFav;
    private ImageView imgFavPoster;
    private TextView txFavOverview;

    private TvPopularData tvPopularData;

    private TvHelper tvHelper;
    ProgressBar progressBarFav;
    FloatingActionButton floatingActionButtonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_fav);

        txFavTitle = findViewById(R.id.tx_movieFav);
        txFavDate = findViewById(R.id.tx_tvFav_date);
        txFavVote = findViewById(R.id.voteTvFav);
        txFavOverview = findViewById(R.id.overviewFav);
        imgFavPoster = findViewById(R.id.imageViewFav);
        imgBackgroundFav = findViewById(R.id.imgTopFavTvMovie);
        txFavChart = findViewById(R.id.chartTvFav);

        progressBarFav = findViewById(R.id.progressbar_favTv);

        floatingActionButtonDelete = findViewById(R.id.fab_delTv);
        progressBarFav.setVisibility(View.VISIBLE);

        tvHelper = new TvHelper(this);
        tvHelper.open();

        final TvFavData tvFavData = getIntent().getParcelableExtra(EXTRA_TV_FAV);
        if (tvFavData != null) {
            txFavTitle.setText(tvFavData.getName());
            txFavDate.setText(tvFavData.getFirstAirDate());
            String voteNumber = tvFavData.getVoteAverage().toString();
            String percent = "/10";
            txFavVote.setText(voteNumber + percent);
            txFavChart.setText(String.valueOf(tvFavData.getPopularity()));
            txFavOverview.setText(tvFavData.getOverview());
            Glide.with(this)
                    .load(tvFavData.getPosterPath())
                    .apply(new RequestOptions().override(500, 219))
                    .into(imgBackgroundFav);
            Glide.with(this)
                    .load(tvFavData.getPosterPath())
                    .into(imgFavPoster);

            progressBarFav.setVisibility(View.GONE);

            floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteAlertDialog(tvFavData);
                }
            });
        } else {
            progressBarFav.setVisibility(View.VISIBLE);
        }
    }

    private void showDeleteAlertDialog(final TvFavData tvFavData) {
        String dialogMessage = getString(R.string.question);
        String dialogTitle = getString(R.string.dialog_tv_title);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteMovie(tvFavData);
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

    private void deleteMovie(TvFavData tvFavData) {
        String id = String.valueOf(tvFavData.getId());
        int rowsDeleted = tvHelper.deleteProvider(id);
        if (rowsDeleted == 0) {
            Toast.makeText(this, tvFavData.getName() + " " + getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, tvFavData.getName() + " " + getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
