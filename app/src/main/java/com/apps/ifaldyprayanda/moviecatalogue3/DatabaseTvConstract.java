package com.apps.ifaldyprayanda.moviecatalogue3;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseTvConstract {

    public static String TABEL_TV = "tv";


    public static final class TvColumns implements BaseColumns {
        public static String TV_TITLE = "title";
        public static String TV_OVERVIEW = "overview";
        public static String TV_DATE = "date";
        public static String TV_POSTER = "poster";
        public static String TV_VOTE = "vote";
        public static String TV_CHART = "chart";
    }

    public static final String TV_AUTHORITY = "com.apps.ifaldyprayanda.moviecatalogue3";

    public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme("content")
            .authority(TV_AUTHORITY)
            .appendPath(TABEL_TV)
            .build();

    public static long getColumnTvLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getColumnTvString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnTvInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
