package com.example.rxdemo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.rxdemo.data.remote.AnimeRepository;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final AnimeRepository animeRepository;

    MainActivityViewModelFactory(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(animeRepository);
    }
}
