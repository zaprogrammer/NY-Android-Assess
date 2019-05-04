package com.elshaabany.nyTimes.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Medium implements Parcelable {

    public static TypeAdapter<Medium> typeAdapter(Gson gson) {
        return new AutoValue_Medium.GsonTypeAdapter(gson);
    }

    @SerializedName("copyright")
    @Nullable
    public abstract String copyright();

    @SerializedName("media-metadata")
    public abstract List<MediaMetadatum> mediaMetadata();

    @SerializedName("subtype")
    @Nullable
    public abstract String subtype();

    @SerializedName("caption")
    @Nullable
    public abstract String caption();

    @SerializedName("type")
    @Nullable
    public abstract String type();

    @SerializedName("approved_for_syndication")
    public abstract int approvedForSyndication();
}