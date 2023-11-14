package com.example.apiapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    // создание экземпляров ViewModel
    private final RequestManager requestManager;

    public ViewModelFactory(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // метод create вызывается, когда требуется создать новый экземпляр ViewModel
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(requestManager);
        }
        // если все верно, то создается новый экземпляр ViewModel, если нет, то выдает ошибку.
        throw new IllegalArgumentException("Неизвестный NewModel класс.");
    }
}

