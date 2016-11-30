package com.charlesmadere.hummingbird.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ObjectResponse<T extends DataObject> extends AbsResponse {

    @Nullable
    @SerializedName("data")
    private T mData;


    @Nullable
    @Override
    public T getData() {
        return mData;
    }

    @Override
    public boolean hasData() {
        return mData != null;
    }

}
