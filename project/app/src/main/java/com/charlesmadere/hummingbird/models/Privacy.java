package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum Privacy implements Parcelable {

    @SerializedName("private")
    PRIVATE(R.string._private),

    @SerializedName("public")
    PUBLIC(R.string._public);

    @StringRes
    private final int mTextResId;


    Privacy(@StringRes final int textResId) {
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

    public static final Creator<Privacy> CREATOR = new Creator<Privacy>() {
        @Override
        public Privacy createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public Privacy[] newArray(final int size) {
            return new Privacy[size];
        }
    };

}
