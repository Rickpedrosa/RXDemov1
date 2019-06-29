package com.example.rxdemo.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeService {
    private static AnimeService INSTANCE;
    private final AnimeRepository chuckService;
    public static final int LIMIT = 20;
    public static final int PLUS_OFFSET = 20;

    private AnimeService(AnimeRepository chuckService) {
        this.chuckService = chuckService;
    }

    public static AnimeService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnimeService(buildInstance());
        }
        return INSTANCE;
    }

    private static AnimeRepository buildInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(AnimeRepository.class);
    }

    public AnimeRepository getChuckService() {
        return chuckService;
    }
}
