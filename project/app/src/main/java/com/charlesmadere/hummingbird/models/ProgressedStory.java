package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class ProgressedStory extends AbsStoryV3 {

    protected ProgressedStory(final Action action) {
        super(action);
    }

    private ProgressedStory(final Parcel source) {
        super(source);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof ProgressedStory && getId().equals(((ProgressedStory) o).getId());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<ProgressedStory> CREATOR = new Creator<ProgressedStory>() {
        @Override
        public ProgressedStory createFromParcel(final Parcel source) {
            return new ProgressedStory(source);
        }

        @Override
        public ProgressedStory[] newArray(final int size) {
            return new ProgressedStory[size];
        }
    };

}
