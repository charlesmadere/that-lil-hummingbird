package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;

public enum WatchingStatusUpdate implements Parcelable {

    COMPLETED(R.string.completed),
    CURRENTLY_WATCHING(R.string.currently_watching),
    DROPPED(R.string.dropped),
    ON_HOLD(R.string.on_hold),
    PLAN_TO_WATCH(R.string.plan_to_watch),
    REMOVE_FROM_LIBRARY(R.string.remove_from_library);

    @StringRes
    private final int mTextResId;


    WatchingStatusUpdate(@StringRes final int textResId) {
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

    public static final Creator<WatchingStatusUpdate> CREATOR = new Creator<WatchingStatusUpdate>() {
        @Override
        public WatchingStatusUpdate createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public WatchingStatusUpdate[] newArray(final int size) {
            return new WatchingStatusUpdate[size];
        }
    };

}
