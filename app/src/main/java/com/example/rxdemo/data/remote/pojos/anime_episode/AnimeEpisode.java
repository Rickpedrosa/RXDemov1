
package com.example.rxdemo.data.remote.pojos.anime_episode;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnimeEpisode {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("meta")
    @Expose
    private Meta_ meta;
    @SerializedName("links")
    @Expose
    private LinksPagination links;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Meta_ getMeta() {
        return meta;
    }

    public void setMeta(Meta_ meta) {
        this.meta = meta;
    }

    public LinksPagination getLinks() {
        return links;
    }

    public void setLinks(LinksPagination links) {
        this.links = links;
    }

}
