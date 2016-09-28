package com.charlesmadere.hummingbird.models;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.ActivityFeedActivity;
import com.charlesmadere.hummingbird.activities.CurrentUserAnimeLibraryActivity;
import com.charlesmadere.hummingbird.activities.CurrentUserMangaLibraryActivity;
import com.google.gson.annotations.SerializedName;

public enum LaunchScreen implements Parcelable {

    @SerializedName("activity_feed")
    ACTIVITY_FEED(R.string.activity_feed) {
        @Override
        Intent createIntent(final Context context) {
            return ActivityFeedActivity.getLaunchIntent(context);
        }
    },

    @SerializedName("anime_library")
    ANIME_LIBRARY(R.string.anime_library) {
        @Override
        Intent createIntent(final Context context) {
            return CurrentUserAnimeLibraryActivity.getLaunchIntent(context);
        }
    },

    @SerializedName("manga_library")
    MANGA_LIBRARY(R.string.manga_library) {
        @Override
        Intent createIntent(final Context context) {
            return CurrentUserMangaLibraryActivity.getLaunchIntent(context);
        }
    };

    @StringRes
    private final int mTextResId;


    LaunchScreen(@StringRes final int textResId) {
        mTextResId = textResId;
    }

    abstract Intent createIntent(final Context context);

    public Intent getLaunchIntent(final Context context) {
        return createIntent(context)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public Intent getRestartAppLaunchIntent(final Context context) {
        return getLaunchIntent(context).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public static final Creator<LaunchScreen> CREATOR = new Creator<LaunchScreen>() {
        @Override
        public LaunchScreen createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public LaunchScreen[] newArray(final int size) {
            return new LaunchScreen[size];
        }
    };

}
