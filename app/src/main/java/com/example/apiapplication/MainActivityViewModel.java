package com.example.apiapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apiapplication.Models.NewsApiResponse;
import com.example.apiapplication.Models.NewsHeadlines;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<NewsHeadlines>> newsHeadlinesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final RequestManager requestManager;

    public MainActivityViewModel(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public LiveData<List<NewsHeadlines>> getNewsHeadlinesLiveData() {
        return newsHeadlinesLiveData;
    }

    public void fetchNewsHeadlines(String category, String query) {
        // метод для получения новостей category и query
        disposables.add(requestManager.getNewsHeadlines(new OnFetchDataListener<NewsApiResponse>() {
            // создается контейнер для всех активных подписок rxJava
            // нужен он типа что если удалить ViewModel то управлять подписаками можно было
            // для выполнения ассинхронного запроса новостей на сервер
            @Override
            public void onFetchData(List<NewsHeadlines> list, String message) {
                newsHeadlinesLiveData.postValue(list);
            }
            // устанавливает значение list в объекте newsHeadlinesLiveData

            @Override
            public void onError(String message) {
                errorMessageLiveData.postValue(message);
            }
        }, category, query));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
        // все объекты вида disposable будут очищены при завершении программы
    }
}

