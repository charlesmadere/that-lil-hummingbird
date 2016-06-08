package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;

import java.util.concurrent.TimeUnit;

public enum PollFrequency implements Parcelable {

    HOURLY(R.string.hourly, TimeUnit.HOURS.toSeconds(1L)),
    EVERY_FOUR_HOURS(R.string.every_four_hours, TimeUnit.HOURS.toSeconds(4L)),
    EVERY_EIGHT_HOURS(R.string.every_eight_hours, TimeUnit.HOURS.toSeconds(8L)),
    DAILY(R.string.daily, TimeUnit.DAYS.toSeconds(1L)),
    EVERY_THREE_DAYS(R.string.every_three_days, TimeUnit.DAYS.toSeconds(3L)),
    WEEKLY(R.string.weekly, TimeUnit.DAYS.toSeconds(7L));

    @StringRes
    private final int mTextResId;

    private final long mPeriod;


    PollFrequency(@StringRes final int textResId, final long period) {
        mTextResId = textResId;
        mPeriod = period;
    }

    /**
     * this is in seconds
     */
    public long getPeriod() {
        return mPeriod;
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
