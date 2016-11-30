package com.charlesmadere.hummingbird.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArrayResponse<T extends DataObject> extends AbsResponse {

    @Nullable
    @SerializedName("data")
    private ArrayList<T> mData;


    @Nullable
    @Override
    public ArrayList<T> getData() {
        return mData;
    }

    @Override
    public boolean hasData() {
        return mData != null && !mData.isEmpty();
    }

}
