package com.example.apiapplication;

import com.example.apiapplication.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse>{

    // для обработки результатов запроса данных из API
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
