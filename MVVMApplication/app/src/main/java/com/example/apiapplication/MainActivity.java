package com.example.apiapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apiapplication.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Получение новостных статей.");
        dialog.show();

        RequestManager requestManager = new RequestManager(this);
        ViewModelFactory factory = new ViewModelFactory(requestManager);

        viewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        // Создаем сам ViewModel
        viewModel.getNewsHeadlinesLiveData().observe(this, this::showNews);
        // Наблюдаем за данными в ViewModel
        viewModel.fetchNewsHeadlines("general", null);
        // Запрашиваем данные через ViewModel
    }

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", headlines));
        // data - ключ для получения данных в активности
    }
}