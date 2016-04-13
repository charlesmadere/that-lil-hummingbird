package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum AgeRating implements Parcelable {

    @SerializedName("G")
    G,

    @SerializedName("PG")
    PG,

    @SerializedName("PG13")
    PG13,

    @SerializedName("R17+")
    R17,

    @SerializedName("R18+")
    R18;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<AgeRating> CREATOR = new Creator<AgeRating>() {
        @Override
        public AgeRating createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public AgeRating[] newArray(final int size) {
            return new AgeRating[size];
        }
    };

}
