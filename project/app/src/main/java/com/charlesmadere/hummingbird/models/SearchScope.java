package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum SearchScope implements Parcelable {

    @SerializedName("all")
    ALL,

    @SerializedName("anime")
    ANIME,

    @SerializedName("groups")
    GROUPS,

    @SerializedName("manga")
    MANGA,

    @SerializedName("users")
    USERS;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<SearchScope> CREATOR = new Creator<SearchScope>() {
        @Override
        public SearchScope createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public SearchScope[] newArray(final int size) {
            return new SearchScope[size];
        }
    };

}
