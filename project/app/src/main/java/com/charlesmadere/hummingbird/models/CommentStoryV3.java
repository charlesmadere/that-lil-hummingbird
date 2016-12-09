package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class CommentStoryV3 extends AbsStoryV3 {

    protected CommentStoryV3(final Parcel source) {
        super(source);
    }

    protected CommentStoryV3(final String id) {
        super(id);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof CommentStoryV3 && getId().equalsIgnoreCase(((CommentStoryV3) o).getId());
    }

    @Override
    public Verb getVerb() {
        return Verb.COMMENT;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<CommentStoryV3> CREATOR = new Creator<CommentStoryV3>() {
        @Override
        public CommentStoryV3 createFromParcel(final Parcel source) {
            return new CommentStoryV3(source);
        }

        @Override
        public CommentStoryV3[] newArray(final int size) {
            return new CommentStoryV3[size];
        }
    };

}
