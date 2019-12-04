package com.apps.ifaldyprayanda.favapp;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.CHART;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.DATE;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.OVERVIEW;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.POSTER;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.TITLE;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.MovieColumns.VOTE;

public class MappingHelper {

    public static ArrayList<FavMovieItem> mapCursorToArray(Cursor cursorNote)
    {
        ArrayList<FavMovieItem> favMovieItem = new ArrayList<>();

//        favMovieItem = new FavMovieItem(cursorNote);

        while (cursorNote.moveToNext())
        {
            int id = cursorNote.getInt(cursorNote.getColumnIndexOrThrow(_ID));
            String title = cursorNote.getString(cursorNote.getColumnIndexOrThrow(TITLE));
            String releaseDate = cursorNote.getString(cursorNote.getColumnIndexOrThrow(DATE));
            String voteOverage = cursorNote.getString(cursorNote.getColumnIndexOrThrow(VOTE));
            String imgPoster = cursorNote.getString(cursorNote.getColumnIndexOrThrow(POSTER));
            String overview = cursorNote.getString(cursorNote.getColumnIndexOrThrow(OVERVIEW));
            String chart = cursorNote.getString(cursorNote.getColumnIndexOrThrow(CHART));
            favMovieItem.add(new FavMovieItem(id, title, releaseDate, voteOverage, imgPoster, overview, chart));
        }
        return favMovieItem;
    }
}
