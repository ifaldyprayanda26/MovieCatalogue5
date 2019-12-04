package com.apps.ifaldyprayanda.moviecatalogue3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.apps.ifaldyprayanda.moviecatalogue3.data.TvPopularData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TvPopularAdapter extends RecyclerView.Adapter<TvPopularAdapter.CardViewViewHolder> {

    private ArrayList<TvPopularData> tvPopularData;

    private OnItemClickCallback onItemClickCallback;

    public TvPopularAdapter(ArrayList<TvPopularData> tvPopularData) {
        this.tvPopularData = tvPopularData;
    }

    public void setTvShowPopularData(ArrayList<TvPopularData> itemData) {
        tvPopularData.clear();
        tvPopularData.addAll(itemData);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv_show_popular, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder cardViewViewHolder, int i) {
        cardViewViewHolder.bind(tvPopularData.get(i));
        cardViewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(tvPopularData.get(cardViewViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvPopularData.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgPopTv;
        final TextView namePopTv;
        final TextView datePopTv;
        final TextView voteTv;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            namePopTv = itemView.findViewById(R.id.tv_title);
            datePopTv = itemView.findViewById(R.id.tv_date);
            imgPopTv = itemView.findViewById(R.id.img_tv_pop);
            voteTv = itemView.findViewById(R.id.tv_vote);
        }

        void bind(TvPopularData tvPopularData) {
            namePopTv.setText(tvPopularData.getName());
            datePopTv.setText(tvPopularData.getFirstAirDate());
            String voteNumber = (tvPopularData.getVoteAverage().toString());
            String percent = "/10";
            String vote = voteNumber + percent;
            voteTv.setText(vote);
            Glide.with(itemView)
                    .load(tvPopularData.getPosterPath())
                    .into(imgPopTv);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvPopularData tvPop);
    }
}
