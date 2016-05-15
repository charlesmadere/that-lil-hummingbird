package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum MangaType implements Parcelable {

    @SerializedName("Manga")
    MANGA(R.string.manga);

    private final int mTextResId;


    MangaType(@StringRes final int textResId) {
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

    public static final Creator<MangaType> CREATOR = new Creator<MangaType>() {
        @Override
        public MangaType createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public MangaType[] newArray(final int size) {
            return new MangaType[size];
        }
    };

}
