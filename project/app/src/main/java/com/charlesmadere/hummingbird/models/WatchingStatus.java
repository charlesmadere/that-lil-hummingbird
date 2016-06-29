package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum WatchingStatus implements Parcelable {

    @SerializedName("completed")
    COMPLETED(R.string.completed_library_is_empty, R.string.error_loading_completed_library,
            R.string.completed),

    @SerializedName("currently-watching")
    CURRENTLY_WATCHING(R.string.currently_watching_library_is_empty,
            R.string.error_loading_currently_watching_library, R.string.currently_watching),

    @SerializedName("dropped")
    DROPPED(R.string.dropped_library_is_empty, R.string.error_loading_dropped_library,
            R.string.dropped),

    @SerializedName("on-hold")
    ON_HOLD(R.string.on_hold_library_is_empty, R.string.error_loading_on_hold_library,
            R.string.on_hold),

    @SerializedName("plan-to-watch")
    PLAN_TO_WATCH(R.string.plan_to_watch_library_is_empty,
            R.string.error_loading_plan_to_watch_library, R.string.plan_to_watch),

    @SerializedName("remove-from-library")
    REMOVE_FROM_LIBRARY(0, 0, R.string.remove_from_library);

    @StringRes
    private final int mEmptyTextResId;

    @StringRes
    private final int mErrorTextResId;

    @StringRes
    private final int mTextResId;


    public static boolean equals(@Nullable final WatchingStatus lhs,
            @Nullable final WatchingStatus rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equals(rhs);
    }

    WatchingStatus(@StringRes final int emptyTextResId, @StringRes final int errorTextResId,
            @StringRes final int textResId) {
        mEmptyTextResId = emptyTextResId;
        mErrorTextResId = errorTextResId;
        mTextResId = textResId;
    }

    @StringRes
    public int getEmptyTextResId() {
        return mEmptyTextResId;
    }

    @StringRes
    public int getErrorTextResId() {
        return mErrorTextResId;
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

    public static final Creator<WatchingStatus> CREATOR = new Creator<WatchingStatus>() {
        @Override
        public WatchingStatus createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public WatchingStatus[] newArray(final int size) {
            return new WatchingStatus[size];
        }
    };

}
