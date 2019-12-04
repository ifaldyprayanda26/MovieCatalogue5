package com.apps.ifaldyprayanda.moviecatalogue3.notification;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String MOVIE_CATALOGUE = "MOVIE_CATALOGUE";

    public static final String MOVIE_TITLE = "MOVIE_TITLE";
    public static final String STATUS_DAILY_NOTIF = "STATUS_DAILY_NOTIF";
    public static final String STATUS_NEW_RELEASE_NOTIF = "STATUS_NEW_RELEASE_NOTIF";

    private final SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public SharedPreference(Context context) {
        preferences = context.getSharedPreferences(MOVIE_CATALOGUE, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveString(String keySp, String value)
    {
        editor.putString(keySp, value);
        editor.commit();
    }

    public void saveBoolean(String keySp, boolean value)
    {
        editor.putBoolean(keySp, value);
        editor.commit();
    }

    public String getNewReleaseMovieTitle()
    {
        return preferences.getString(MOVIE_TITLE, "");
    }

    public Boolean getStatusDaily()
    {
        return preferences.getBoolean(STATUS_DAILY_NOTIF, false);
    }

    public Boolean getStatusNewRelease()
    {
        return preferences.getBoolean(STATUS_NEW_RELEASE_NOTIF, false);
    }
}
