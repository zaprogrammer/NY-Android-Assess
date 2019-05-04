package com.elshaabany.nyTimes.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Result implements Parcelable {

    public static TypeAdapter<Result> typeAdapter(Gson gson) {
        return new AutoValue_Result.GsonTypeAdapter(gson);
    }

    @SerializedName("column")
    @Nullable
    public abstract String column();

    @SerializedName("section")
    public abstract String section();

    @SerializedName("abstract")
    public abstract String jsonMemberAbstract();

    @SerializedName("source")
    public abstract String source();

    @SerializedName("asset_id")
    public abstract long assetId();

    @SerializedName("media")
    public abstract List<Medium> media();

    @SerializedName("type")
    public abstract String type();

    @SerializedName("title")
    public abstract String title();

    @SerializedName("url")
    public abstract String url();

    @SerializedName("adx_keywords")
    public abstract String adxKeywords();

    @SerializedName("id")
    public abstract long id();

    @SerializedName("byline")
    public abstract String byline();

    @SerializedName("published_date")
    public abstract String publishedDate();

    @SerializedName("views")
    public abstract int views();
}