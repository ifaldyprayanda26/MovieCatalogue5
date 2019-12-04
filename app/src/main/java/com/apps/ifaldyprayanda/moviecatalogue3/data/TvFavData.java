package com.apps.ifaldyprayanda.moviecatalogue3.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract;
import com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract;

import static android.provider.BaseColumns._ID;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.getColumnInt;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseConstract.getColumnString;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_CHART;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_DATE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_OVERVIEW;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_POSTER;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_TITLE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.TvColumns.TV_VOTE;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.getColumnTvInt;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.getColumnTvLong;
import static com.apps.ifaldyprayanda.moviecatalogue3.DatabaseTvConstract.getColumnTvString;

public class TvFavData implements Parcelable {
    private int id;
    private String name;
    private String posterPath;
    private Number voteAverage;
    private Number popularity;
    private String firstAirDate;
    private String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
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
        dest.writeString(this.name);
        dest.writeString(this.posterPath);
        dest.writeSerializable(this.voteAverage);
        dest.writeSerializable(this.popularity);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
    }

    public TvFavData(Cursor cursor) {
        this.id = getColumnTvInt(cursor, _ID);
        this.name = getColumnTvString(cursor, DatabaseTvConstract.TvColumns.TV_TITLE);
        this.firstAirDate = getColumnTvString(cursor, DatabaseTvConstract.TvColumns.TV_DATE);
        this.voteAverage = getColumnTvInt(cursor, DatabaseTvConstract.TvColumns.TV_VOTE);
        this.popularity = getColumnTvInt(cursor, DatabaseTvConstract.TvColumns.TV_CHART);
        this.overview = getColumnTvString(cursor, DatabaseTvConstract.TvColumns.TV_OVERVIEW);
        this.posterPath = getColumnTvString(cursor, DatabaseTvConstract.TvColumns.TV_POSTER);
    }

    protected TvFavData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = (Number) in.readSerializable();
        this.popularity = (Number) in.readSerializable();
        this.firstAirDate = in.readString();
        this.overview = in.readString();
    }

    public static final Creator<TvFavData> CREATOR = new Creator<TvFavData>() {
        @Override
        public TvFavData createFromParcel(Parcel source) {
            return new TvFavData(source);
        }

        @Override
        public TvFavData[] newArray(int size) {
            return new TvFavData[size];
        }
    };
}
