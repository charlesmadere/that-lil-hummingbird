package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable {

    // TODO


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(final Parcel source) {
            final Group g = new Group();
            // TODO
            return g;
        }

        @Override
        public Group[] newArray(final int size) {
            return new Group[size];
        }
    };

}
