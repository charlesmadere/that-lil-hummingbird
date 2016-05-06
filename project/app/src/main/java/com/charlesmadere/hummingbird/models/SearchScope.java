package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum SearchScope implements Parcelable {

    @SerializedName("all")
    ALL(R.string.all),

    @SerializedName("anime")
    ANIME(R.string.anime),

    @SerializedName("groups")
    GROUPS(R.string.groups),

    @SerializedName("manga")
    MANGA(R.string.manga),

    @SerializedName("users")
    USERS(R.string.users);

    private final int mTextResId;


    SearchScope(@StringRes final int textResId) {
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
