package com.example.rxdemo.data.remote;

import com.example.rxdemo.data.remote.pojos.anime.Anime;
import com.example.rxdemo.data.remote.pojos.anime_episode.AnimeEpisode;

import io.reactivex.Observable;
import retrofit2.Response;

public class AnimeRepositoryImpl implements AnimeRepository {
    @Override
    public Observable<Response<Anime>> getAnime(String id) {
        return AnimeService.getInstance().getChuckService().getAnime(id);
    }

    @Override
    public Observable<Response<AnimeEpisode>> getAnimeEpisodes(String id, int offset, int limit) {
        return AnimeService.getInstance().getChuckService().getAnimeEpisodes(
                id,
                offset,
                AnimeService.LIMIT);
    }

    @Override
    public Observable<AnimeEpisode> getAnimeEpisodesV2(String id, int offset, int limit) {
        return AnimeService.getInstance().getChuckService().getAnimeEpisodesV2(
                id,
                offset,
                AnimeService.LIMIT);
    }
}
