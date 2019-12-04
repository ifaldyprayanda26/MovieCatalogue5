package com.apps.ifaldyprayanda.moviecatalogue3.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbfav";
    private static final String CREATE_TABEL_TV = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseTvConstract.TABEL_TV,
            DatabaseTvConstract.TvColumns._ID,
            DatabaseTvConstract.TvColumns.TV_TITLE,
            DatabaseTvConstract.TvColumns.TV_DATE,
            DatabaseTvConstract.TvColumns.TV_POSTER,
            DatabaseTvConstract.TvColumns.TV_OVERVIEW,
            DatabaseTvConstract.TvColumns.TV_VOTE,
            DatabaseTvConstract.TvColumns.TV_CHART);
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABEL = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseConstract.MovieColumns.TABEL,
            DatabaseConstract.MovieColumns._ID,
            DatabaseConstract.MovieColumns.TITLE,
            DatabaseConstract.MovieColumns.DATE,
            DatabaseConstract.MovieColumns.POSTER,
            DatabaseConstract.MovieColumns.OVERVIEW,
            DatabaseConstract.MovieColumns.VOTE,
            DatabaseConstract.MovieColumns.CHART);


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABEL);
        db.execSQL(CREATE_TABEL_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstract.MovieColumns.TABEL);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseTvConstract.TABEL_TV);
        onCreate(db);
    }

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
