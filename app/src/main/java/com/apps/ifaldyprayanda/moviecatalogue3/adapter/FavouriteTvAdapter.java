package com.apps.ifaldyprayanda.moviecatalogue3.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.CustomOnItemClickListener;
import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.TvFavData;
import com.apps.ifaldyprayanda.moviecatalogue3.details.DetailTvFavActivity;
import com.bumptech.glide.Glide;

public class FavouriteTvAdapter extends RecyclerView.Adapter<FavouriteTvAdapter.TvFavViewHolder>  {

    private Context mContext;
    private Cursor mListTv;

    @NonNull
    @Override
    public TvFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_tv, parent, false);
        return new TvFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvFavViewHolder holder, int position)
    {
        final TvFavData tvFavData = getItemTv(position);

        holder.textTvFav.setText(tvFavData.getName());
        holder.dateTvFav.setText(tvFavData.getFirstAirDate());
        String voteNumber = tvFavData.getVoteAverage().toString();
        String percent = "/10";
        String tvVote = voteNumber + percent;
        holder.voteTvFav.setText(tvVote);
        Glide.with(mContext)
                .load(tvFavData.getPosterPath())
                .into(holder.imgTvFav);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int mPosition) {
                Intent intent = new Intent(mContext, DetailTvFavActivity.class);
                intent.putExtra(DetailTvFavActivity.EXTRA_TV_FAV, tvFavData);
                mContext.startActivity(intent);
//                Toast.makeText(mContext, "You Clicked " + tvFavData.getName(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (mListTv == null)
        {
            return  0;
        }
        return mListTv.getCount();
    }

    public class TvFavViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTvFav;
        TextView textTvFav;
        TextView dateTvFav;
        TextView voteTvFav;
        public TvFavViewHolder(@NonNull View itemView) {
            super(itemView);
            textTvFav = itemView.findViewById(R.id.tv_titleFav);
            dateTvFav = itemView.findViewById(R.id.tv_dateFav);
            voteTvFav = itemView.findViewById(R.id.tv_voteFav);
            imgTvFav = itemView.findViewById(R.id.img_tvfav);
        }
    }

    private TvFavData getItemTv(int position)
    {
        if (!mListTv.moveToPosition(position))
        {
            throw new IllegalStateException("Invalid Position");
        }
        return new TvFavData(mListTv);
    }

    public FavouriteTvAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    public void setListTv(Cursor mListTv)
    {
        this.mListTv = mListTv;
    }

}
