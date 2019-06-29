package com.example.rxdemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rxdemo.base.recycler.Resource;
import com.example.rxdemo.data.remote.AnimeRepository;
import com.example.rxdemo.data.remote.AnimeService;
import com.example.rxdemo.data.remote.pojos.anime_episode.AnimeEpisode;
import com.example.rxdemo.data.remote.pojos.anime_episode.Datum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private Disposable disposable;
    private int counter = 0;
    private AnimeRepository animeRepository;
    private MutableLiveData<Boolean> progressTrigger = new MutableLiveData<>();
    private MutableLiveData<List<Datum>> episodes = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> callState = new MutableLiveData<>();

    MainActivityViewModel(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    void callKitsuApi() {
        String id = "1555"; // naruto shippuden
        //ON NEXT
        //ON ERROR
        //ON COMPLETE
        String totalFormat = "/%d";
        callState.postValue(Resource.loading());
        disposable = getEpisodesFromResponse(id, 0, String.format(Locale.US, totalFormat, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeEpisode -> {
                    progressTrigger.postValue(true);
                    episodes.postValue(animeEpisode.getData());
                }, throwable -> {
                    progressTrigger.postValue(false);
                    callState.postValue(Resource.error(throwable));
                    resetCounter();
                }, () -> {
                    progressTrigger.postValue(false);
                    resetCounter();
                });
    }

    LiveData<Boolean> getProgressTrigger() {
        return progressTrigger;
    }

    LiveData<Resource<String>> getCallState() {
        return callState;
    }

    LiveData<List<Datum>> getEpisodes() {
        return episodes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    private Observable<AnimeEpisode> getEpisodesFromResponse(String id, int offset, String total) {
        return animeRepository.getAnimeEpisodes(id, offset, AnimeService.LIMIT)
                .flatMap((Function<Response<AnimeEpisode>, ObservableSource<AnimeEpisode>>) animeEpisodeResponse ->
                        Observable.just(Objects.requireNonNull(animeEpisodeResponse.body())))
                .concatMap(animeEpisode -> {
                    updateCallProgress(animeEpisode, total);
                    if (animeEpisode.getLinks().getNext() == null) {
                        return animeRepository.getAnimeEpisodes(id, offset, AnimeService.LIMIT)
                                .flatMap((Function<Response<AnimeEpisode>, ObservableSource<AnimeEpisode>>) animeEpisodeResponse ->
                                        Observable.just(Objects.requireNonNull(animeEpisodeResponse.body())));
                    } else {
                        return Observable.zip(animeRepository.getAnimeEpisodes(id, offset, AnimeService.LIMIT)
                                        .flatMap((Function<Response<AnimeEpisode>, ObservableSource<AnimeEpisode>>) animeEpisodeResponse ->
                                                Observable.just(Objects.requireNonNull(animeEpisodeResponse.body()))),
                                getEpisodesFromResponse(id, (offset + AnimeService.PLUS_OFFSET), total),
                                (epOne, epTwo) -> {
                                    AnimeEpisode theTrueOne = new AnimeEpisode();
                                    List<Datum> datum = new ArrayList<>(epOne.getData());
                                    datum.addAll(epTwo.getData());
                                    theTrueOne.setMeta(epTwo.getMeta());
                                    theTrueOne.setLinks(epTwo.getLinks());
                                    theTrueOne.setData(datum);
                                    return theTrueOne;
                                });
                    }
                });
    }

    private void updateCallProgress(AnimeEpisode animeEpisode, String total) {
        progressTrigger.postValue(true);
        counter += animeEpisode.getData().size();
        callState.postValue(Resource.success(String.valueOf(counter).concat(total)));
    }

    private void resetCounter() {
        counter = 0;
    }
}
