package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum Verb implements Parcelable {

    @SerializedName("comment")
    COMMENT,

    @SerializedName("follow")
    FOLLOW,

    @SerializedName("post")
    POST,

    @SerializedName("progressed")
    PROGRESSED,

    @SerializedName("rated")
    RATED,

    @SerializedName("reviewed")
    REVIEWED,

    @SerializedName("updated")
    UPDATED;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<Verb> CREATOR = new Creator<Verb>() {
        @Override
        public Verb createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public Verb[] newArray(final int size) {
            return new Verb[size];
        }
    };

}
