package com.example.portalberita.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.portalberita.models.EverythingFilter;
import com.example.portalberita.models.NewsResponse;
import com.example.portalberita.models.TopHeadlinesFilter;
import com.example.portalberita.retrofit_api.NewsRepository;


public class NewsViewModel extends ViewModel {

    private MutableLiveData<NewsResponse> mutableLiveData;
    private NewsRepository newsRepository;
    private String apiKey = "49838933209641c5a0db769f3429e83c";

    //Inisiasi menampilkan berita top headline dari negara indonesia
    public void init(TopHeadlinesFilter filter){
        if (mutableLiveData != null){
            return;
        }
        newsRepository = NewsRepository.getInstance();
        mutableLiveData = newsRepository.getTopNews(filter, this.apiKey);
    }

    //Cari berita top headline berdasarkan filter
    public void getTopHeadline(TopHeadlinesFilter filter){
        newsRepository = NewsRepository.getInstance();
        mutableLiveData = newsRepository.getTopNews(filter, this.apiKey);
    }

    //Cari semua berita berdasarkan filter
    public void getEverything(EverythingFilter filter){
        newsRepository = NewsRepository.getInstance();
        mutableLiveData = newsRepository.getEveryNews(filter, this.apiKey);
    }


    public LiveData<NewsResponse> getNewsRepository() {
        return mutableLiveData;
    }
}
