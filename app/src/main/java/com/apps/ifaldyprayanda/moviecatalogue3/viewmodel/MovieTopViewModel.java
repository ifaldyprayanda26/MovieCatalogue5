package com.apps.ifaldyprayanda.moviecatalogue3.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieTopViewModel extends ViewModel {
    String API_KEY = "cfed702593ebdac39044981c42203c72";
    private MutableLiveData<ArrayList<MovieTopData>> listMovieTop = new MutableLiveData<>();

    public void setListMovieTop(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieTopData> listMovieItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray listMv = responseObject.getJSONArray("results");

                    for (int i = 0; i < listMv.length(); i++) {
                        JSONObject movie = listMv.getJSONObject(i);
                        MovieTopData tMovie = new MovieTopData(movie);
                        listMovieItem.add(tMovie);
                    }
                    listMovieTop.postValue(listMovieItem);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fail to Load Data", error.getMessage());
            }
        });
    }

    public void searchMovie(final Context context, String query)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieTopData> listMovieItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray listMv = responseObject.getJSONArray("results");

                    for (int i = 0; i < listMv.length(); i++) {
                        JSONObject movie = listMv.getJSONObject(i);
                        MovieTopData tMovie = new MovieTopData(movie);
                        listMovieItem.add(tMovie);
                    }
                    listMovieTop.postValue(listMovieItem);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fail to Load Data", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieTopData>> getListMovieTop() {
        return listMovieTop;
    }
}
