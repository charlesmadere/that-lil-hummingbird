package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum SearchDepth implements Parcelable {

    @SerializedName("instant")
    INSTANT;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<SearchDepth> CREATOR = new Creator<SearchDepth>() {
        @Override
        public SearchDepth createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public SearchDepth[] newArray(final int size) {
            return new SearchDepth[size];
        }
    };

}
