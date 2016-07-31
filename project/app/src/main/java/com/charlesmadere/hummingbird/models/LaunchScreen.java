package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum LaunchScreen implements Parcelable {

    @SerializedName("anime_library")
    ANIME_LIBRARY(R.string.anime_library),

    @SerializedName("feed")
    FEED(R.string.feed),

    @SerializedName("manga_library")
    MANGA_LIBRARY(R.string.manga_library);

    @StringRes
    private final int mTextResId;


    LaunchScreen(@StringRes final int textResId) {
        mTextResId = textResId;
    }

    @StringRes
    public int getTextResId() {
        return mTextResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<LaunchScreen> CREATOR = new Creator<LaunchScreen>() {
        @Override
        public LaunchScreen createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public LaunchScreen[] newArray(final int size) {
            return new LaunchScreen[size];
        }
    };

}
