package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class ReviewedStory extends AbsStoryV3 {

    protected ReviewedStory(final Parcel source) {
        super(source);
    }

    protected ReviewedStory(final String id) {
        super(id);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof ReviewedStory && getId().equals(((ReviewedStory) o).getId());
    }

    @Override
    public Verb getVerb() {
        return Verb.REVIEWED;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<ReviewedStory> CREATOR = new Creator<ReviewedStory>() {
        @Override
        public ReviewedStory createFromParcel(final Parcel source) {
            return new ReviewedStory(source);
        }

        @Override
        public ReviewedStory[] newArray(final int size) {
            return new ReviewedStory[size];
        }
    };

}
