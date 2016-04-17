package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Season implements Parcelable {

    private final int mSeason;


    public Season(final int season) {
        mSeason = season;
    }

    public int getSeason() {
        return mSeason;
    }

    @Override
    public String toString() {
        return String.valueOf(mSeason);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mSeason);
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(final Parcel source) {
            return new Season(source.readInt());
        }

        @Override
        public Season[] newArray(final int size) {
            return new Season[size];
        }
    };

}
