package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum AiringStatus implements Parcelable {

    @SerializedName("Currently Airing")
    CURRENTLY_AIRING(R.string.currently_airing),

    @SerializedName("Finished Airing")
    FINISHED_AIRING(R.string.finished_airing),

    @SerializedName("Not Yet Aired")
    NOT_YET_AIRED(R.string.not_yet_aired);


    private final int mTextResId;


    AiringStatus(@StringRes final int textResId) {
        mTextResId = textResId;
    }

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

    public static final Creator<AiringStatus> CREATOR = new Creator<AiringStatus>() {
        @Override
        public AiringStatus createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public AiringStatus[] newArray(final int size) {
            return new AiringStatus[size];
        }
    };

}
