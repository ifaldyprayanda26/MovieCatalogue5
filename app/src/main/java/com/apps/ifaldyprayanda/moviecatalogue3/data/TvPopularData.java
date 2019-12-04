package com.apps.ifaldyprayanda.moviecatalogue3.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class TvPopularData implements Parcelable {
    private String name;
    private Number popularity;
    private int voteCount;
    private String firstAirDate;
    private String backdropPath;
    private String originalLanguage;
    private int id;
    private Number voteAverage;
    private String overview;
    private String posterPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Number getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeSerializable(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.id);
        dest.writeSerializable(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
    }

    public TvPopularData(JSONObject object) {
        try {
            this.name = object.getString("name");
            this.popularity = (Number) object.get("popularity");
            this.voteCount = object.getInt("vote_count");
            this.firstAirDate = object.getString("first_air_date");
            String backdrop = object.getString("backdrop_path");
            this.backdropPath = "https://image.tmdb.org/t/p/w500/" + backdrop;
            this.originalLanguage = object.getString("original_language");
            this.id = object.getInt("id");
            this.voteAverage = (Number) object.get("vote_average");
            this.overview = object.getString("overview");
            String poster = object.getString("poster_path");
            this.posterPath = "https://image.tmdb.org/t/p/w185/" + poster;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error Data", e.getMessage());
        }
    }

    protected TvPopularData(Parcel in) {
        this.name = in.readString();
        this.popularity = (Number) in.readSerializable();
        this.voteCount = in.readInt();
        this.firstAirDate = in.readString();
        this.backdropPath = in.readString();
        this.originalLanguage = in.readString();
        this.id = in.readInt();
        this.voteAverage = (Number) in.readSerializable();
        this.overview = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<TvPopularData> CREATOR = new Creator<TvPopularData>() {
        @Override
        public TvPopularData createFromParcel(Parcel in) {
            return new TvPopularData(in);
        }

        @Override
        public TvPopularData[] newArray(int size) {
            return new TvPopularData[size];
        }
    };
}
