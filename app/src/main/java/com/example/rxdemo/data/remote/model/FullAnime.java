package com.example.rxdemo.data.remote.model;

import com.example.rxdemo.data.remote.pojos.anime.Anime;
import com.example.rxdemo.data.remote.pojos.anime_episode.AnimeEpisode;

import java.util.List;

public class FullAnime {

    private Anime animeInfo;
    private AnimeEpisode episodes;

    public FullAnime(Anime animeInfo, AnimeEpisode episodes) {
        this.animeInfo = animeInfo;
        this.episodes = episodes;
    }

    public Anime getAnimeInfo() {
        return animeInfo;
    }

    public void setAnimeInfo(Anime animeInfo) {
        this.animeInfo = animeInfo;
    }

    public AnimeEpisode getEpisodes() {
        return episodes;
    }

    public void setEpisodes(AnimeEpisode episodes) {
        this.episodes = episodes;
    }
}
