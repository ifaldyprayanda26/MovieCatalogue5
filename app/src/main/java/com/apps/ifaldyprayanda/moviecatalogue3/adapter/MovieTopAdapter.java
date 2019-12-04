package com.apps.ifaldyprayanda.moviecatalogue3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieTopAdapter extends RecyclerView.Adapter<MovieTopAdapter.CardViewViewHolder> {

    private ArrayList<MovieTopData> movieTopData;
    private OnItemClickCallback onItemClickCallback;

    public MovieTopAdapter(ArrayList<MovieTopData> movieTopData) {
        this.movieTopData = movieTopData;
    }

    public void setMovieTopData(ArrayList<MovieTopData> tData) {
        movieTopData.clear();
        movieTopData.addAll(tData);
        notifyDataSetChanged();
    }

    public void setOnItemCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View tView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new CardViewViewHolder(tView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder cardViewViewHolder, int i) {
        cardViewViewHolder.bind(movieTopData.get(i));
        cardViewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(movieTopData.get(cardViewViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieTopData.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTopMovie;
        TextView textTopMovie;
        TextView textRelease;
        TextView txVote;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTopMovie = itemView.findViewById(R.id.img_movie);
            textTopMovie = itemView.findViewById(R.id.tx_title);
            textRelease = itemView.findViewById(R.id.tx_date);
            txVote = itemView.findViewById(R.id.movie_vote);
        }

        void bind(final MovieTopData movieTopData) {
            textTopMovie.setText(movieTopData.getTitle());
            textRelease.setText(movieTopData.getReleaseDate());
            String voteNumber = (movieTopData.getVoteAverage().toString());
            String percent = "/10";
            String movieVote = voteNumber + percent;
            txVote.setText(movieVote);
            Glide.with(itemView)
                    .load(movieTopData.getPosterPath())
                    .into(imgTopMovie);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieTopData movieTop);
    }
}
