package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.charlesmadere.hummingbird.R;

public class CommentReplyNotification extends AbsNotification implements Parcelable {

    @Override
    public CharSequence getText(final Resources res) {
        return res.getString(R.string.x_wrote_a_reply_to_your_comment, getUser().getId());
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
