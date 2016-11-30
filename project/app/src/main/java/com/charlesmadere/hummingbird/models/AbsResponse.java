package com.charlesmadere.hummingbird.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public abstract class AbsResponse {

    @Nullable
    @SerializedName("included")
    private ArrayList<DataObject> mIncluded;

    @Nullable
    @SerializedName("links")
    private Links mLinks;

    @Nullable
    @SerializedName("meta")
    private Meta mMeta;


    @Nullable
    public abstract Object getData();

    @Nullable
    public ArrayList<DataObject> getIncluded() {
        return mIncluded;
    }

    @Nullable
    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    public Meta getMeta() {
        return mMeta;
    }

    public abstract boolean hasData();

    public boolean hasIncluded() {
        return mIncluded != null && !mIncluded.isEmpty();
    }

    public boolean hasLinks() {
        return mLinks != null;
    }

    public boolean hasMeta() {
        return mMeta != null;
    }

}
