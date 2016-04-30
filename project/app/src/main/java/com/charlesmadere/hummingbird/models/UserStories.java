package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserStories implements Parcelable {

    // TODO


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<UserStories> CREATOR = new Creator<UserStories>() {
        @Override
        public UserStories createFromParcel(final Parcel source) {
            // TODO
            return null;
        }

        @Override
        public UserStories[] newArray(final int size) {
            return new UserStories[size];
        }
    };

}
