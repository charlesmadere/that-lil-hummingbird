package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {

    // TODO


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(final Parcel source) {
            // TODO
            return null;
        }

        @Override
        public Feed[] newArray(final int size) {
            return new Feed[size];
        }
    };

}
