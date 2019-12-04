package com.apps.ifaldyprayanda.moviecatalogue3.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieFavData;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.CHART;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.DATE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.OVERVIEW;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.POSTER;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.TITLE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.VOTE;

public class MovieHelper {

    private static String DATABASE_TABLE = DatabaseConstract.MovieColumns.TABEL;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public MovieHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mDatabase = mDatabaseHelper.getReadableDatabase();
    }

    public long insertProvider(ContentValues contentValues) {
        return mDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public Cursor queryByIdProvider(String id) {
        return mDatabase.query(DATABASE_TABLE, null, _ID + "=?", new String[]{id}, null, null, null, null);
    }

    public int updateProvider(String id, ContentValues contentValues) {
        return mDatabase.update(DATABASE_TABLE, contentValues, _ID + "=?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return mDatabase.delete(DATABASE_TABLE, _ID + "=?", new String[]{id});
    }

    public Cursor queryProvider() {
        return mDatabase.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC");
    }
}
