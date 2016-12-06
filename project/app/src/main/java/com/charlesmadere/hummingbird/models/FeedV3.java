package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;

public class FeedV3 implements Hydratable, Parcelable {

    // TODO


    @Override
    @WorkerThread
    public void hydrate() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {

    }

    public static final Creator<FeedV3> CREATOR = new Creator<FeedV3>() {
        @Override
        public FeedV3 createFromParcel(final Parcel source) {
            final FeedV3 f = new FeedV3();

            return f;
        }

        @Override
        public FeedV3[] newArray(final int size) {
            return new FeedV3[size];
        }
    };

}
