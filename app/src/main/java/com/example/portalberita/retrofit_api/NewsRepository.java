package com.example.portalberita.retrofit_api;


import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.portalberita.models.EverythingFilter;
import com.example.portalberita.models.NewsResponse;
import com.example.portalberita.models.TopHeadlinesFilter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static NewsRepository newsRepository;

    public static NewsRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    private NewsApi newsApi;

    public NewsRepository(){
        newsApi = RetrofitService.cteateService(NewsApi.class);
    }

    public MutableLiveData<NewsResponse> getTopNews(TopHeadlinesFilter filter, String key){
        MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getTopHeadline(filter.getCountry(), filter.getSearchKey(), filter.getCategory(), key)
                .enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call,
                                   Response<NewsResponse> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }else{
                    System.out.println("Error From NEWS API !!\nERROR CODE: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

    public MutableLiveData<NewsResponse> getEveryNews(EverythingFilter filter, String key){
        MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getEverything(filter.getSearchKey(), filter.getFromDate(), filter.getToDate(), filter.getSortBy(), key)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call,
                                           Response<NewsResponse> response) {
                        if (response.isSuccessful()){
                            newsData.setValue(response.body());
                        }
                        else{
                            System.out.println("Error From NEWS API !!\nERROR CODE: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        newsData.setValue(null);
                    }
                });
        return newsData;
    }
}
