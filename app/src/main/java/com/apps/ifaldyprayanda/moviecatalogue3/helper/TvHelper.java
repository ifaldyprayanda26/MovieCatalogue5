package com.apps.ifaldyprayanda.moviecatalogue3.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TABEL_TV;

public class TvHelper {

    private static String DATABASE_TABLE_TV = TABEL_TV;
    private Context tContext;
    private DatabaseHelper tDatabaseHelper;
    private SQLiteDatabase tDatabase;

    public TvHelper(Context tContext) {
        this.tContext = tContext;
    }

    public void open() throws SQLException {
        tDatabaseHelper = new DatabaseHelper(tContext);
        tDatabase = tDatabaseHelper.getWritableDatabase();
    }

    public long insertProvider(ContentValues contentTvValues) {
        return tDatabase.insert(DATABASE_TABLE_TV, null, contentTvValues);
    }

    public void close() {
        tDatabaseHelper.close();
    }

    public Cursor queryByIdProvider(String id) {
        return tDatabase.query(DATABASE_TABLE_TV, null, _ID + "=?", new String[]{id}, null, null, null, null);
    }

    public int updateProvider(String id, ContentValues contentTvValues) {
        return tDatabase.update(DATABASE_TABLE_TV, contentTvValues, _ID + "=?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return tDatabase.delete(DATABASE_TABLE_TV, _ID + "=?", new String[]{id});
    }

    public Cursor queryProvider() {
        return tDatabase.query(DATABASE_TABLE_TV, null, null, null, null, null, _ID + " DESC");
    }


}
