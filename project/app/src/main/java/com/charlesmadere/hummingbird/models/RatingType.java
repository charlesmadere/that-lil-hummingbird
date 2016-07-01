package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum RatingType implements Parcelable {

    @SerializedName("advanced")
    ADVANCED,

    @SerializedName("simple")
    SIMPLE;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<RatingType> CREATOR = new Creator<RatingType>() {
        @Override
        public RatingType createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public RatingType[] newArray(final int size) {
            return new RatingType[size];
        }
    };

}
