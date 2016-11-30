package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum DataType implements Parcelable {

    @SerializedName("activities")
    ACTIVITIES,

    @SerializedName("activityGroups")
    ACTIVITY_GROUPS,

    @SerializedName("anime")
    ANIME,

    @SerializedName("castings")
    CASTINGS,

    @SerializedName("characters")
    CHARACTERS,

    @SerializedName("episodes")
    EPISODES,

    @SerializedName("favorites")
    FAVORITES,

    @SerializedName("follows")
    FOLLOWS,

    @SerializedName("franchises")
    FRANCHISES,

    @SerializedName("genres")
    GENRES,

    @SerializedName("installments")
    INSTALLMENTS,

    @SerializedName("libraryEntries")
    LIBRARY_ENTRIES,

    @SerializedName("manga")
    MANGA,

    @SerializedName("people")
    PEOPLE,

    @SerializedName("posts")
    POSTS,

    @SerializedName("reviews")
    REVIEWS,

    @SerializedName("streamers")
    STREAMERS,

    @SerializedName("streamingLinks")
    STREAMING_LINKS,

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

    public static final Creator<DataType> CREATOR = new Creator<DataType>() {
        @Override
        public DataType createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public DataType[] newArray(final int size) {
            return new DataType[size];
        }
    };

}
