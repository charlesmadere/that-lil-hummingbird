package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsStoryV3 implements Parcelable {

    private final Action mAction;


    protected AbsStoryV3(final Action action) {
        mAction = action;
    }

    protected AbsStoryV3(final Parcel source) {
        mAction = source.readParcelable(Action.class.getClassLoader());
    }

    @Override
    public abstract boolean equals(final Object o);

    public Action getAction() {
        return mAction;
    }

    public String getId() {
        return mAction.getId();
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    protected void hydrate(final FeedV3 feed) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mAction, flags);
    }

}
