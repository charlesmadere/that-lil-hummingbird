package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

public class MediaStory extends AbsStory {

    @Override
    public Type getType() {
        return Type.MEDIA_STORY;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);

    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);

    }

    public static final Creator<MediaStory> CREATOR = new Creator<MediaStory>() {
        @Override
        public MediaStory createFromParcel(final Parcel source) {
            final MediaStory ms = new MediaStory();
            ms.readFromParcel(source);
            return ms;
        }

        @Override
        public MediaStory[] newArray(final int size) {
            return new MediaStory[size];
        }
    };

}
