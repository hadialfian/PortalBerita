package com.example.portalberita.retrofit_api;

import com.example.portalberita.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadline(@Query("country") String country,
                                       @Query("q") String searchKey,
                                       @Query("category") String category,
                                       @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getEverything(@Query("q") String searchKey,
                                      @Query("from") String fromDate,
                                      @Query("to") String toDate,
                                      @Query("sortBy") String sortBy,
                                      @Query("apiKey") String apiKey);
}
