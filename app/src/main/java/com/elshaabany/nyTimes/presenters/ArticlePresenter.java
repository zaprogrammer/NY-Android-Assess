package com.elshaabany.nyTimes.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import com.elshaabany.nyTimes.BuildConfig;
import com.elshaabany.nyTimes.activities.MainActivity;
import com.elshaabany.nyTimes.api.ArticleServiceApi;
import com.elshaabany.nyTimes.components.DaggerSharedPreferenceComponent;
import com.elshaabany.nyTimes.models.Articles;
import com.elshaabany.nyTimes.models.Result;
import com.elshaabany.nyTimes.modules.SharedPreferenceModule;
import com.elshaabany.nyTimes.utils.Constants;
import com.elshaabany.nyTimes.utils.LocalUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */
public class ArticlePresenter {
    private MainActivity mView;
    private Retrofit retrofit;
    private SharedPreferences mSharedPreferences;

    private List<Result> resultsList;


    public ArticlePresenter(MainActivity view, Retrofit retrofit) {
        this.mView = view;
        this.retrofit = retrofit;
        // Dagger%COMPONENT_NAME%
        mSharedPreferences = DaggerSharedPreferenceComponent.builder()
                .sharedPreferenceModule(new SharedPreferenceModule(mView.getApplication()))
                .build().getSharedPreference();
    }

    /**
     * Notify Presenter to start fetching data.
     *
     * @param isRefresh
     */
    public void loadPosts(final boolean isRefresh) {
        if (LocalUtils.isConnected(mView)) {
            retrofit.create(ArticleServiceApi.class)
                    .getArticles(Constants.DEFAULT_LAST_DAYS_FILTER, BuildConfig.API_KEY)
                    .enqueue(new Callback<Articles>() {
                        @Override
                        public void onResponse(Call<Articles> call, Response<Articles> response) {
                            resultsList = response.body().results();

                            Log.i("Result => ", resultsList.toString());

                            saveArticleToSharePreference(resultsList);
                            loadArticles(isRefresh, resultsList);
                        }

                        @Override
                        public void onFailure(Call<Articles> call, Throwable t) {
                            Log.e(ArticlePresenter.class.getName(), t.toString());
                            mView.displayErrorSnackbar();
                        }
                    });

        } else {
            List<Result> events = getDataFromSharedPreferences();
            if (events == null) {
                LocalUtils.buildNoNetworkDialog(mView).show();
                mView.displayErrorSnackbar();
                mView.hideProgress();
            } else {
                loadArticles(isRefresh, events);
            }
        }
    }

    private void loadArticles(boolean isRefresh, List<Result> articles) {
        mView.hideProgress();
        if (isRefresh) {
            mView.refreshPosts(articles);
        } else {
            mView.addPosts(articles);
        }
    }

    private void saveArticleToSharePreference(List<Result> data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(data);

        editor.putString(Constants.FEED_KEY, json);
        editor.apply();
    }

    private List<Result> getDataFromSharedPreferences() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(Constants.FEED_KEY, null);
        Type type = new TypeToken<ArrayList<Result>>() {
        }.getType();
        ArrayList<Result> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}