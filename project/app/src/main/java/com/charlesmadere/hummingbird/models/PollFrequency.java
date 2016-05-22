package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;

public enum PollFrequency implements Parcelable {

    HOURLY(R.string.hourly),
    EVERY_FOUR_HOURS(R.string.every_four_hours),
    EVERY_EIGHT_HOURS(R.string.every_eight_hours),
    DAILY(R.string.daily),
    EVERY_THREE_DAYS(R.string.every_three_days),
    WEEKLY(R.string.weekly);

    @StringRes
    private final int mTextResId;


    PollFrequency(@StringRes final int textResId) {
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

    public static final Creator<PollFrequency> CREATOR = new Creator<PollFrequency>() {
        @Override
        public PollFrequency createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public PollFrequency[] newArray(final int size) {
            return new PollFrequency[size];
        }
    };

}
