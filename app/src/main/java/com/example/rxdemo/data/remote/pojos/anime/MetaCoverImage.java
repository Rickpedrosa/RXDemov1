
package com.example.rxdemo.data.remote.pojos.anime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaCoverImage {

    @SerializedName("dimensions")
    @Expose
    private DimensionsCoverImage dimensions;

    public DimensionsCoverImage getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsCoverImage dimensions) {
        this.dimensions = dimensions;
    }

}
