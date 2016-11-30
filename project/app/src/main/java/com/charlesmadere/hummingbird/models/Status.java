package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum Status implements Parcelable {

    @SerializedName("completed")
    COMPLETED,

    @SerializedName("current")
    CURRENT,

    @SerializedName("dropped")
    DROPPED,

    @SerializedName("on_hold")
    ON_HOLD,

    @SerializedName("planned")
    PLANNED;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public Status[] newArray(final int size) {
            return new Status[size];
        }
    };

}
