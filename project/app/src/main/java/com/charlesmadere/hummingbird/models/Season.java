package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;

public class Season implements Parcelable {

    @Nullable
    private final Integer mSeason;


    public Season(@Nullable final Integer season) {
        mSeason = season;
    }

    @Nullable
    public Integer getSeason() {
        return mSeason;
    }

    public boolean hasSeason() {
        return mSeason != null;
    }

    @Override
    public String toString() {
        if (hasSeason()) {
            return mSeason.toString();
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeInteger(mSeason, dest);
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(final Parcel source) {
            return new Season(ParcelableUtils.readInteger(source));
        }

        @Override
        public Season[] newArray(final int size) {
            return new Season[size];
        }
    };

}
