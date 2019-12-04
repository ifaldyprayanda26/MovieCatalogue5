package com.apps.ifaldyprayanda.moviecatalogue3.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.CustomOnItemClickListener;
import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieFavData;
import com.apps.ifaldyprayanda.moviecatalogue3.details.DetailMovieFavActivity;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.CONTENT_URI;


public class FavouritAdapter extends RecyclerView.Adapter<FavouritAdapter.MovieFavViewHolder> {

    private Cursor mListMovies;
    private Context mContext;

    public void setListMovies(Cursor mListMovies) {
        this.mListMovies = mListMovies;
    }

    @NonNull
    @Override
    public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_movie, parent, false);
        return new MovieFavViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavViewHolder holder, final int position) {
        final MovieFavData movieFavData = getItem(position);
        holder.textFavMovie.setText(movieFavData.getTitle());
        holder.textReleaseFav.setText(movieFavData.getReleaseDate());
        String voteNumber = movieFavData.getVoteAverage().toString();
        String percent = "/10";
        String movieVote = voteNumber + percent;
        holder.txVoteFav.setText(movieVote);
        Glide.with(mContext)
                .load(movieFavData.getPosterPath())
                .into(holder.imgFavMovie);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int mPosition) {
                Intent intent = new Intent(mContext, DetailMovieFavActivity.class);
                intent.putExtra(DetailMovieFavActivity.EXTRA_MOVIE_FAV, movieFavData);
                mContext.startActivity(intent);
//                Toast.makeText(mContext, "You Clicked " + movieFavData.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (mListMovies == null) {
            return 0;
        }
        return mListMovies.getCount();
    }

    public class MovieFavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFavMovie;
        TextView textFavMovie;
        TextView textReleaseFav;
        TextView txVoteFav;

        public MovieFavViewHolder(@NonNull View itemView) {
            super(itemView);
            textFavMovie = itemView.findViewById(R.id.tx_titleFav);
            textReleaseFav = itemView.findViewById(R.id.tx_dateFav);
            txVoteFav = itemView.findViewById(R.id.movie_voteFav);
            imgFavMovie = itemView.findViewById(R.id.img_movieFav);
        }
    }

    private MovieFavData getItem(int position) {
        if (!mListMovies.moveToPosition(position)) {
            throw new IllegalStateException("Invalid Position");
        }
        return new MovieFavData(mListMovies);
    }

    public FavouritAdapter(Context mContext) {
        this.mContext = mContext;
    }


}
