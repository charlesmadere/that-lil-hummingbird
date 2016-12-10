package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class UpdatedStory extends AbsStoryV3 {

    protected UpdatedStory(final Action action) {
        super(action);
    }

    private UpdatedStory(final Parcel source) {
        super(source);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof UpdatedStory && getId().equals(((UpdatedStory) o).getId());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<UpdatedStory> CREATOR = new Creator<UpdatedStory>() {
        @Override
        public UpdatedStory createFromParcel(final Parcel source) {
            return new UpdatedStory(source);
        }

        @Override
        public UpdatedStory[] newArray(final int size) {
            return new UpdatedStory[size];
        }
    };

}
