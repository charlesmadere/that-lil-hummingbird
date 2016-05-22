package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;

public enum SyncFrequency implements Parcelable {

    HOURLY(R.string.hourly),
    EVERY_FOUR_HOURS(R.string.every_four_hours),
    EVERY_EIGHT_HOURS(R.string.every_eight_hours),
    DAILY(R.string.daily),
    EVERY_THREE_DAYS(R.string.every_three_days),
    WEEKLY(R.string.weekly);

    @StringRes
    private final int mTextResId;


    SyncFrequency(@StringRes final int textResId) {
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

    public static final Creator<SyncFrequency> CREATOR = new Creator<SyncFrequency>() {
        @Override
        public SyncFrequency createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public SyncFrequency[] newArray(final int size) {
            return new SyncFrequency[size];
        }
    };

}
