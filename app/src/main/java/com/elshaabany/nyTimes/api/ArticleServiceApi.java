package com.elshaabany.nyTimes.api;

import com.elshaabany.nyTimes.models.Articles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */
public interface ArticleServiceApi {
    @GET("svc/mostpopular/v2/viewed/{daysFilter}.json")
    Call<Articles> getArticles(@Path("daysFilter") int daysFilter, @Query("api-key") String API_KEY);
}