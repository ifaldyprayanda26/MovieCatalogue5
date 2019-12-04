package com.apps.ifaldyprayanda.moviecatalogue3.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.CHART;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.DATE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.OVERVIEW;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.POSTER;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.TITLE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.MovieColumns.VOTE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.getColumnInt;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.getColumnString;

public class MovieFavData implements Parcelable {
    private int id;
    private String title;
    private String posterPath;
    private Number voteAverage;
    private Number popularity;
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

    public Number getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
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
        dest.writeSerializable(this.voteAverage);
        dest.writeSerializable(this.popularity);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
    }

    public MovieFavData(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseConstract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseConstract.MovieColumns.OVERVIEW);
        this.posterPath = getColumnString(cursor, DatabaseConstract.MovieColumns.POSTER);
        this.voteAverage = getColumnInt(cursor, DatabaseConstract.MovieColumns.VOTE);
        this.popularity = getColumnInt(cursor, DatabaseConstract.MovieColumns.CHART);
        this.releaseDate = getColumnString(cursor, DatabaseConstract.MovieColumns.DATE);
    }

    protected MovieFavData(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = (Number) in.readSerializable();
        this.popularity = (Number) in.readSerializable();
        this.releaseDate = in.readString();
        this.overview = in.readString();
    }

    public static final Creator<MovieFavData> CREATOR = new Creator<MovieFavData>() {
        @Override
        public MovieFavData createFromParcel(Parcel source) {
            return new MovieFavData(source);
        }

        @Override
        public MovieFavData[] newArray(int size) {
            return new MovieFavData[size];
        }
    };
}
