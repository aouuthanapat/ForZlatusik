package com.example.apiapplication;

import android.content.Context;
import android.widget.Toast;

import com.example.apiapplication.Models.NewsApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    private final Context context;
    private final NewsApiService newsApiService;

    public RequestManager(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // этот адаптер позволяет использовать rxJava для управления асинхронными вывозвами и обработки результатов
                .build();

        newsApiService = retrofit.create(NewsApiService.class);
    }

    public Disposable getNewsHeadlines(OnFetchDataListener<NewsApiResponse> listener, String category, String query) {
        // создается тот самый асинхронный запрос к API
        Observable<NewsApiResponse> observable = newsApiService.callHeadlines("us", "health", query, context.getString(R.string.api_key))
                // отвечает за предоставление асинхронного потока данных observable и получен он будет из callHeadlines
                .subscribeOn(Schedulers.io())
                // указывает на каком потоке будет должен выполняться запрос к серверу у меня это schedulers.io
                .observeOn(AndroidSchedulers.mainThread());
        // указывает на каком потоке должно выполняться обновление пользовательского интерфейса
        // будут они отображены на главном потоке с помощью AndroidSchedulers.mainThread

        return observable.subscribe(
                // шо же делать с полученными потоками?))))))))))))
                response -> {
                    if (response != null) {
                        Toast.makeText(context, "Загрузка прошла успешно.", Toast.LENGTH_SHORT).show();
                        listener.onFetchData(response.getArticles(), "");
                    } else {
                        listener.onError("Пустой ответ от сервера.");
                    }
                },
                throwable -> listener.onError(throwable.getMessage())
                // лямбда
        );
    }

    public interface NewsApiService {
        @GET("top-headlines")
        Observable<NewsApiResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String apiKey
        );
    }
}