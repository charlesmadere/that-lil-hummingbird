package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsStoryV3 implements Parcelable {

    private final Action mMainAction;
    private final ActionGroup mActionGroup;


    protected AbsStoryV3(final ActionGroup actionGroup, final Action mainAction) {
        mActionGroup = actionGroup;
        mMainAction = mainAction;
    }

    protected AbsStoryV3(final Parcel source) {
        mActionGroup = source.readParcelable(ActionGroup.class.getClassLoader());
        mMainAction = source.readParcelable(Action.class.getClassLoader());
    }

    @Override
    public abstract boolean equals(final Object o);

    public ActionGroup getActionGroup() {
        return mActionGroup;
    }

    public String getId() {
        return mActionGroup.getId();
    }

    public Action getMainAction() {
        return mMainAction;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    protected void hydrate(final FeedV3 feed) {
        // TODO
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mActionGroup, flags);
        dest.writeParcelable(mMainAction, flags);
    }

}
