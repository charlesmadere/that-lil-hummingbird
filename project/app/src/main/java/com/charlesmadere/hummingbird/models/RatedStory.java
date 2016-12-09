package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class RatedStory extends AbsStoryV3 {

    protected RatedStory(final Parcel source) {
        super(source);
    }

    protected RatedStory(final String id) {
        super(id);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof RatedStory && getId().equals(((RatedStory) o).getId());
    }

    @Override
    public Verb getVerb() {
        return Verb.RATED;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<RatedStory> CREATOR = new Creator<RatedStory>() {
        @Override
        public RatedStory createFromParcel(final Parcel source) {
            return new RatedStory(source);
        }

        @Override
        public RatedStory[] newArray(final int size) {
            return new RatedStory[size];
        }
    };

}
