package com.apps.ifaldyprayanda.favapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.getColumnInt;
import static com.apps.ifaldyprayanda.favapp.DatabaseConstract.getColumnString;


public class FavMovieItem implements Parcelable {
    private int id;
    private String title;
    private String posterPath;
    private String voteAverage;
    private String popularity;
    private String releaseDate;
    private String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteAverage);
        dest.writeString(this.popularity);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
    }

    public FavMovieItem(int id, String title, String releaseDate, String voteOverage, String imgPoster, String overview, String chart)
    {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteOverage;
        this.popularity = chart;
        this.overview = overview;
        this.posterPath = imgPoster;
    }

    public FavMovieItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseConstract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseConstract.MovieColumns.OVERVIEW);
        this.posterPath = getColumnString(cursor, DatabaseConstract.MovieColumns.POSTER);
        this.voteAverage = getColumnString(cursor, DatabaseConstract.MovieColumns.VOTE);
        this.popularity = getColumnString(cursor, DatabaseConstract.MovieColumns.CHART);
        this.releaseDate = getColumnString(cursor, DatabaseConstract.MovieColumns.DATE);
    }

    protected FavMovieItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.popularity = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
    }

    public static final Creator<FavMovieItem> CREATOR = new Creator<FavMovieItem>() {
        @Override
        public FavMovieItem createFromParcel(Parcel source) {
            return new FavMovieItem(source);
        }

        @Override
        public FavMovieItem[] newArray(int size) {
            return new FavMovieItem[size];
        }
    };
}
