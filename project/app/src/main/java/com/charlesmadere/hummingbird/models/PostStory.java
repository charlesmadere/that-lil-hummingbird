package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class PostStory extends AbsStoryV3 {

    protected PostStory(final Action action) {
        super(action);
    }

    private PostStory(final Parcel source) {
        super(source);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof PostStory && getId().equals(((PostStory) o).getId());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<PostStory> CREATOR = new Creator<PostStory>() {
        @Override
        public PostStory createFromParcel(final Parcel source) {
            return new PostStory(source);
        }

        @Override
        public PostStory[] newArray(final int size) {
            return new PostStory[size];
        }
    };

}
