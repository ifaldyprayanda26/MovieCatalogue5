package com.apps.ifaldyprayanda.favapp;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener{

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
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, mPosition);
    }
}
