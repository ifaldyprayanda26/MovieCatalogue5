package com.apps.ifaldyprayanda.moviecatalogue3.widget;

import android.database.Cursor;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.TITLE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.getColumnInt;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.getColumnString;

public class ResultItem {
    private String overview;
    private String title;
    private String posterPath;
    private String releaseDate;
    private int id;

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public ResultItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseConstract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseConstract.MovieColumns.OVERVIEW);
        this.posterPath = getColumnString(cursor, DatabaseConstract.MovieColumns.POSTER);
    }
}
