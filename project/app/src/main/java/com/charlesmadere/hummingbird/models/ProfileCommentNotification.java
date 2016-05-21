package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileCommentNotification extends AbsNotification implements Parcelable {

    @Override
    public Type getType() {
        return Type.PROFILE_COMMENT;
    }

    public static final Creator<ProfileCommentNotification> CREATOR = new Creator<ProfileCommentNotification>() {
        @Override
        public ProfileCommentNotification createFromParcel(final Parcel source) {
            final ProfileCommentNotification pcn = new ProfileCommentNotification();
            pcn.readFromParcel(source);
            return pcn;
        }

        @Override
        public ProfileCommentNotification[] newArray(final int size) {
            return new ProfileCommentNotification[size];
        }
    };

}
