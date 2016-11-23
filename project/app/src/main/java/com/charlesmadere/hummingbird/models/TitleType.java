package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum TitleType implements Parcelable {

    @SerializedName("canonical")
    CANONICAL(R.string.canonical),

    @SerializedName("english")
    ENGLISH(R.string.english),

    @SerializedName("japanese")
    JAPANESE(R.string.japanese);

    @StringRes
    private final int mTextResId;


    TitleType(@StringRes final int textResId) {
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

    public static final Creator<TitleType> CREATOR = new Creator<TitleType>() {
        @Override
        public TitleType createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public TitleType[] newArray(final int size) {
            return new TitleType[size];
        }
    };

}
