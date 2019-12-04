package com.apps.ifaldyprayanda.moviecatalogue3.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieFavData;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;
import com.bumptech.glide.Glide;

import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;

import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.CONTENT_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;

    private Cursor list;

    StackRemoteViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        ResultItem item = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);

        Bitmap bitmap = null;
        try{
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(item.getPosterPath())
                    .submit(512, 512)
                    .get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch  (ExecutionException e)
        {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imgWidget, bitmap);
        Bundle extras = new Bundle();
        extras.putInt(MovieFavoriteWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imgWidget, fillIntent);
        return rv;
    }

    private ResultItem getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new ResultItem(list);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
