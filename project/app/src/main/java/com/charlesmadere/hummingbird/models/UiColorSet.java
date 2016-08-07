package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

public class UiColorSet implements Parcelable {

    @ColorInt
    private final int mDarkVibrantColor;

    @ColorInt
    private final int mVibrantColor;


    public UiColorSet(@ColorInt final int darkVibrantColor, @ColorInt final int vibrantColor) {
        mDarkVibrantColor = darkVibrantColor;
        mVibrantColor = vibrantColor;
    }

    @ColorInt
    public int getDarkVibrantColor() {
        return mDarkVibrantColor;
    }

    @ColorInt
    public int getVibrantColor() {
        return mVibrantColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mDarkVibrantColor);
        dest.writeInt(mVibrantColor);
    }

    public static final Creator<UiColorSet> CREATOR = new Creator<UiColorSet>() {
        @Override
        public UiColorSet createFromParcel(final Parcel source) {
            return new UiColorSet(source.readInt(), source.readInt());
        }

        @Override
        public UiColorSet[] newArray(final int size) {
            return new UiColorSet[size];
        }
    };

}
