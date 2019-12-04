package com.apps.ifaldyprayanda.favapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.FavMovieViewHolder> {

    private final ArrayList<FavMovieItem> favMovieItem = new ArrayList<>();

    private final Activity activity;


    public FavouriteMovieAdapter(Activity activity)
    {
        this.activity = activity;
    }

    public ArrayList<FavMovieItem> getListFavoriteMovie() {
        return favMovieItem;
    }

    public void setListMovieFav(ArrayList<FavMovieItem> listMovieFavItem)
    {
        this.favMovieItem.clear();
        this.favMovieItem.addAll(listMovieFavItem);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_movie, parent, false);
        return new FavMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavMovieViewHolder holder, final int position) {
        holder.textFavMovie.setText(getListFavoriteMovie().get(position).getTitle());
        holder.textReleaseFav.setText(getListFavoriteMovie().get(position).getReleaseDate());
        String vote = getListFavoriteMovie().get(position).getVoteAverage();
        String percent = "/10";
        String voteNumber = vote + percent;
        holder.txVoteFav.setText(voteNumber);
        Glide.with(holder.itemView.getContext())
                .load(getListFavoriteMovie().get(position).getPosterPath())
                .into(holder.imgFavMovie);

        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int mPosition) {
                Toast.makeText(activity, "You Clicked " + favMovieItem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return favMovieItem.size();
    }

    public class FavMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFavMovie;
        TextView textFavMovie;
        TextView textReleaseFav;
        TextView txVoteFav;

        public FavMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textFavMovie = itemView.findViewById(R.id.tx_titleFav);
            textReleaseFav = itemView.findViewById(R.id.tx_dateFav);
            txVoteFav = itemView.findViewById(R.id.movie_voteFav);
            imgFavMovie = itemView.findViewById(R.id.img_movieFav);
        }
    }
}
