package com.apps.ifaldyprayanda.moviecatalogue3.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.apps.ifaldyprayanda.moviecatalogue3.data.TvPopularData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvPopularViewModel extends ViewModel {
    String API_KEY = "cfed702593ebdac39044981c42203c72";
    private MutableLiveData<ArrayList<TvPopularData>> listTvPopular = new MutableLiveData<>();

    public void setTvPopular(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvPopularData> listTvPopularItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray listTv = responseObject.getJSONArray("results");

                    for (int i = 0; i < listTv.length(); i++) {
                        JSONObject movie = listTv.getJSONObject(i);
                        TvPopularData tvPopularData = new TvPopularData(movie);
                        listTvPopularItem.add(tvPopularData);
                    }
                    listTvPopular.postValue(listTvPopularItem);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }

    public void searchTv(final Context context, String query)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvPopularData> listTvPopularItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray listTv = responseObject.getJSONArray("results");

                    for (int i = 0; i < listTv.length(); i++) {
                        JSONObject movie = listTv.getJSONObject(i);
                        TvPopularData tvPopularData = new TvPopularData(movie);
                        listTvPopularItem.add(tvPopularData);
                    }
                    listTvPopular.postValue(listTvPopularItem);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvPopularData>> getTvPopular() {
        return listTvPopular;
    }
}
