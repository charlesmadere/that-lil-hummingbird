package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FollowedStory extends AbsStory implements Parcelable {

    @Override
    public Type getType() {
        return Type.FOLLOWED;
    }

    public static final Creator<FollowedStory> CREATOR = new Creator<FollowedStory>() {
        @Override
        public FollowedStory createFromParcel(final Parcel source) {
            final FollowedStory fs = new FollowedStory();
            fs.readFromParcel(source);
            return fs;
        }

        @Override
        public FollowedStory[] newArray(final int size) {
            return new FollowedStory[size];
        }
    };

}
