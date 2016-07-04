package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum ReadingStatus implements Parcelable {

    @SerializedName("completed")
    COMPLETED(R.string.completed_library_is_empty, R.string.error_loading_completed_library,
            R.string.completed),

    @SerializedName("currently-reading")
    CURRENTLY_READING(R.string.currently_reading_library_is_empty,
            R.string.error_loading_currently_reading_library, R.string.currently_reading),

    @SerializedName("dropped")
    DROPPED(R.string.dropped_library_is_empty, R.string.error_loading_dropped_library,
            R.string.dropped),

    @SerializedName("on-hold")
    ON_HOLD(R.string.on_hold_library_is_empty, R.string.error_loading_on_hold_library,
            R.string.on_hold),

    @SerializedName("plan-to-read")
    PLAN_TO_READ(R.string.plan_to_read_library_is_empty,
            R.string.error_loading_plan_to_read_library, R.string.plan_to_read);

    @StringRes
    private final int mEmptyTextResId;

    @StringRes
    private final int mErrorTextResId;

    @StringRes
    private final int mTextResId;


    ReadingStatus(@StringRes final int emptyTextResId, @StringRes final int errorTextResId,
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

    public static final Creator<ReadingStatus> CREATOR = new Creator<ReadingStatus>() {
        @Override
        public ReadingStatus createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public ReadingStatus[] newArray(final int size) {
            return new ReadingStatus[size];
        }
    };

}