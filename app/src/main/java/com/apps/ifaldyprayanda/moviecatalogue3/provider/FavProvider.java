package com.apps.ifaldyprayanda.moviecatalogue3.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.MovieHelper;
import com.apps.ifaldyprayanda.moviecatalogue3.helper.TvHelper;

import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.AUTHORITY;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.CONTENT_URI;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.CONTENT_URI_TV;

public class FavProvider extends ContentProvider {

    private static final int MOVIE = 1;

    private static final int MOVIE_ID = 2;

    private static final int TV = 3;

    private static final int TV_ID = 4;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DatabaseConstract.MovieColumns.TABEL, MOVIE);

        URI_MATCHER.addURI(AUTHORITY, DatabaseConstract.MovieColumns.TABEL+"/#", MOVIE_ID);

        URI_MATCHER.addURI(AUTHORITY, DatabaseTvConstract.TABEL_TV, TV);

        URI_MATCHER.addURI(AUTHORITY, DatabaseTvConstract.TABEL_TV+"/#", TV_ID);
    }

    private MovieHelper movieHelper;

    private TvHelper tvHelper;


    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        tvHelper = new TvHelper(getContext());

        movieHelper.open();
        tvHelper.open();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri))
        {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }
        if (cursor != null)
        {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return  cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added = 0;
        switch (URI_MATCHER.match(uri))
        {
            case MOVIE:
                added = movieHelper.insertProvider(values);
                break;
            case MOVIE_ID:
                added = 0;
                break;
            case TV:
                added = tvHelper.insertProvider(values);
                break;
            case TV_ID:
                added = 0;
                break;
        }
        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return  Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri))
        {
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if  (deleted>0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch  (URI_MATCHER.match(uri))
        {
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated>0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
