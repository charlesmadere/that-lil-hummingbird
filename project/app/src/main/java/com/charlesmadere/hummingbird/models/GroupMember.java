package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupMember implements Parcelable {

    // TODO


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<GroupMember> CREATOR = new Creator<GroupMember>() {
        @Override
        public GroupMember createFromParcel(final Parcel source) {
            final GroupMember gm = new GroupMember();
            // TODO
            return gm;
        }

        @Override
        public GroupMember[] newArray(final int size) {
            return new GroupMember[size];
        }
    };

}
