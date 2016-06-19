package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AppNewsStatus implements Parcelable {

    @SerializedName("importantNewsAvailable")
    private boolean mImportantNewsAvailable;

    @SerializedName("pollTime")
    private long mPollTime;


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
            final AppNewsStatus ans = new AppNewsStatus();
            ans.mImportantNewsAvailable = source.readInt() != 0;
            ans.mPollTime = source.readLong();
            return ans;
        }

        @Override
        public AppNewsStatus[] newArray(final int size) {
            return new AppNewsStatus[size];
        }
    };

}
