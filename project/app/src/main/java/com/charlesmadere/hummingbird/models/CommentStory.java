package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class CommentStory extends AbsStory {

    @Override
    public Type getType() {
        return Type.COMMENT;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);

    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<CommentStory> CREATOR = new Creator<CommentStory>() {
        @Override
        public CommentStory createFromParcel(final Parcel source) {
            final CommentStory cs = new CommentStory();
            cs.readFromParcel(source);
            return cs;
        }

        @Override
        public CommentStory[] newArray(final int size) {
            return new CommentStory[size];
        }
    };

}
