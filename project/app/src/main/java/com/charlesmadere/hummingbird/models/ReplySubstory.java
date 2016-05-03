package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

    public void hydrate(final ArrayList<User> users) {
        for (final User user : users) {
            // TODO we need the new AbsUser class
//            if (user.getName().equalsIgnoreCase(mUserId)) {
//                mUser = user;
//                break;
//            }
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
