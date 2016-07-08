package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public enum WatchingStatus implements Parcelable {

    COMPLETED(R.string.completed_library_is_empty, R.string.error_loading_completed_library,
            R.string.completed, new String[] { "Completed" }),

    CURRENTLY_WATCHING(R.string.currently_watching_library_is_empty,
            R.string.error_loading_currently_watching_library, R.string.currently_watching,
            new String[] { "Currently Watching", "currently-watching" }),

    DROPPED(R.string.dropped_library_is_empty, R.string.error_loading_dropped_library,
            R.string.dropped, new String[] { "Dropped" }),

    ON_HOLD(R.string.on_hold_library_is_empty, R.string.error_loading_on_hold_library,
            R.string.on_hold, new String[] { "On Hold", "on-hold" }),

    PLAN_TO_WATCH(R.string.plan_to_watch_library_is_empty,
            R.string.error_loading_plan_to_watch_library, R.string.plan_to_watch,
            new String[] { "Plan to Watch", "plan-to-watch" });

    @StringRes
    private final int mEmptyTextResId;

    @StringRes
    private final int mErrorTextResId;

    @StringRes
    private final int mTextResId;

    private final String[] mValues;


    WatchingStatus(@StringRes final int emptyTextResId, @StringRes final int errorTextResId,
            @StringRes final int textResId, final String[] values) {
        mEmptyTextResId = emptyTextResId;
        mErrorTextResId = errorTextResId;
        mTextResId = textResId;
        mValues = values;
    }

    @StringRes
    public int getEmptyTextResId() {
        return mEmptyTextResId;
    }

    @StringRes
    public int getErrorTextResId() {
        return mErrorTextResId;
    }

    public String getPostValue() {
        return mValues[0];
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

    public static final JsonDeserializer<WatchingStatus> JSON_DESERIALIZER = new JsonDeserializer<WatchingStatus>() {
        @Override
        public WatchingStatus deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonNull()) {
                return null;
            }

            final String jsonValue = json.getAsString();

            if (TextUtils.isEmpty(jsonValue)) {
                return null;
            }

            for (final WatchingStatus watchingStatus : values()) {
                for (final String wsValue : watchingStatus.mValues) {
                    if (wsValue.equalsIgnoreCase(jsonValue)) {
                        return watchingStatus;
                    }
                }
            }

            throw new RuntimeException("unknown " + WatchingStatus.class.getName() +
                    " value: \"" + jsonValue + '"');
        }
    };

}
