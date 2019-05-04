package com.elshaabany.nyTimes.models;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class MediaMetadatum implements Parcelable {

    public static TypeAdapter<MediaMetadatum> typeAdapter(Gson gson) {
        return new AutoValue_MediaMetadatum.GsonTypeAdapter(gson);
    }

    @SerializedName("format")
    public abstract String format();

    @SerializedName("width")
    public abstract int width();

    @SerializedName("url")
    public abstract String url();

    @SerializedName("height")
    public abstract int height();
}