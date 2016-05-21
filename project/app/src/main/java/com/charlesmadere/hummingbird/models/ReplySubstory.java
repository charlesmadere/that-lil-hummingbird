package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReplySubstory extends AbsSubstory implements Parcelable {

    @SerializedName("reply")
    private String mReply;

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    private User mUser;


    public String getReply() {
        return mReply;
    }

    @Override
    public Type getType() {
        return Type.REPLY;
    }

    public User getUser() {
        return mUser;
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public void hydrate(final Feed feed) {
        for (final User user : feed.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
                break;
            }
        }
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mReply = source.readString();
        mUserId = source.readString();
        mUser = source.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mReply);
        dest.writeString(mUserId);
        dest.writeParcelable(mUser, flags);
    }

    public static final Creator<ReplySubstory> CREATOR = new Creator<ReplySubstory>() {
        @Override
        public ReplySubstory createFromParcel(final Parcel source) {
            final ReplySubstory rs = new ReplySubstory();
            rs.readFromParcel(source);
            return rs;
        }

        @Override
        public ReplySubstory[] newArray(final int size) {
            return new ReplySubstory[size];
        }
    };

}
