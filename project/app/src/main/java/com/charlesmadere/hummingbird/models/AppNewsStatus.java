package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AppNewsStatus implements Parcelable {

    @SerializedName("importantNewsAvailable")
    private boolean mImportantNewsAvailable;

    @SerializedName("pollTime")
    private long mPollTime;


    public AppNewsStatus(final boolean importantNewsAvailable) {
        mImportantNewsAvailable = importantNewsAvailable;
    }

    private AppNewsStatus(final Parcel source) {
        mImportantNewsAvailable = source.readInt() != 0;
        mPollTime = source.readLong();
    }

    public long getPollTime() {
        return mPollTime;
    }

    public boolean isImportantNewsAvailable() {
        return mImportantNewsAvailable;
    }

    public void setImportantNewsAvailable(final boolean importantNewsAvailable) {
        mImportantNewsAvailable = importantNewsAvailable;
    }

    public void updatePollTime() {
        mPollTime = System.currentTimeMillis();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mImportantNewsAvailable ? 1 : 0);
        dest.writeLong(mPollTime);
    }

    public static final Creator<AppNewsStatus> CREATOR = new Creator<AppNewsStatus>() {
        @Override
        public AppNewsStatus createFromParcel(final Parcel source) {
            return new AppNewsStatus(source);
        }

        @Override
        public AppNewsStatus[] newArray(final int size) {
            return new AppNewsStatus[size];
        }
    };

}
