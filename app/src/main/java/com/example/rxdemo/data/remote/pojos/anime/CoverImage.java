
package com.example.rxdemo.data.remote.pojos.anime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverImage {

    @SerializedName("tiny")
    @Expose
    private String tiny;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("meta")
    @Expose
    private MetaCoverImage meta;

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public MetaCoverImage getMeta() {
        return meta;
    }

    public void setMeta(MetaCoverImage meta) {
        this.meta = meta;
    }

}
