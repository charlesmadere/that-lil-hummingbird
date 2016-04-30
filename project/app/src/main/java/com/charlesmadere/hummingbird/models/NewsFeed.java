package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsFeed implements Parcelable {

    // TODO


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<NewsFeed> CREATOR = new Creator<NewsFeed>() {
        @Override
        public NewsFeed createFromParcel(final Parcel source) {
            // TODO
            return null;
        }

        @Override
        public NewsFeed[] newArray(final int size) {
            return new NewsFeed[size];
        }
    };

}
