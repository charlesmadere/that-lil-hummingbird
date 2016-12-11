package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum ReplyToType implements Parcelable {

    @SerializedName("comment")
    COMMENT,

    @SerializedName("post")
    POST;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<ReplyToType> CREATOR = new Creator<ReplyToType>() {
        @Override
        public ReplyToType createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public ReplyToType[] newArray(final int size) {
            return new ReplyToType[size];
        }
    };

}
