package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class FollowStory extends AbsStoryV3 {

    protected FollowStory(final Action action) {
        super(action);
    }

    private FollowStory(final Parcel source) {
        super(source);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof FollowStory && getId().equals(((FollowStory) o).getId());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<FollowStory> CREATOR = new Creator<FollowStory>() {
        @Override
        public FollowStory createFromParcel(final Parcel source) {
            return new FollowStory(source);
        }

        @Override
        public FollowStory[] newArray(final int size) {
            return new FollowStory[size];
        }
    };

}
