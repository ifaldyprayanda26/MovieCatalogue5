package com.apps.ifaldyprayanda.moviecatalogue3;

import android.view.View;

import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int mPosition;

    private OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.mPosition = mPosition;

        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int mPosition);
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, mPosition);

    }
}
