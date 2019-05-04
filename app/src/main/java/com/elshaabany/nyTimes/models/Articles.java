package com.elshaabany.nyTimes.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Articles implements Parcelable {

    public static TypeAdapter<Articles> typeAdapter(Gson gson) {
        return new AutoValue_Articles.GsonTypeAdapter(gson);
    }

    @SerializedName("copyright")
    @Nullable
    public abstract String copyright();

    @SerializedName("results")
    public abstract List<Result> results();

    @SerializedName("num_results")
    public abstract int numResults();

    @SerializedName("status")
    public abstract String status();
}