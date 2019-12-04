package com.apps.ifaldyprayanda.favapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.TABEL;

public class DatabaseConstract  {

    public static final class MovieColumns implements BaseColumns {
        public static String TABEL = "movie";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String DATE = "date";
        public static String POSTER = "poster";
        public static String VOTE = "vote";
        public static String CHART = "chart";
    }


    public static final String AUTHORITY = "com.apps.ifaldyprayanda.moviecatalogue3";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABEL)
            .build();

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
