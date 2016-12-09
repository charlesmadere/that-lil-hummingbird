package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsStoryV3 implements Parcelable {

    private String mId;


    protected AbsStoryV3(final Parcel source) {
        mId = source.readString();
    }

    protected AbsStoryV3(final String id) {
        mId = id;
    }

    @Override
    public abstract boolean equals(final Object o);

    public String getId() {
        return mId;
    }

    public abstract Verb getVerb();

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    protected void hydrate(final FeedV3 feed) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mId);
    }

}
