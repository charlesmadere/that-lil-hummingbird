package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

public class CommentReplyNotification extends AbsNotification implements Parcelable {

    @Override
    public CharSequence getText(final Resources res) {
        return null;
    }

    @Override
    public Type getType() {
        return Type.COMMENT_REPLY;
    }

    public static final Creator<CommentReplyNotification> CREATOR = new Creator<CommentReplyNotification>() {
        @Override
        public CommentReplyNotification createFromParcel(final Parcel source) {
            final CommentReplyNotification crn = new CommentReplyNotification();
            crn.readFromParcel(source);
            return crn;
        }

        @Override
        public CommentReplyNotification[] newArray(final int size) {
            return new CommentReplyNotification[size];
        }
    };

}
