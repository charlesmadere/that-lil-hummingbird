package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.annotations.SerializedName;

public class ReplySubstory extends AbsSubstory implements Parcelable {

    @SerializedName("reply")
    private String mReply;

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    private CharSequence mCompiledReply;
    private User mUser;


    public String getPlainTextReply() {
        return mReply;
    }

    public CharSequence getReply() {
        return mCompiledReply;
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
    @WorkerThread
    public void hydrate(final Feed feed) {
        // noinspection ConstantConditions
        for (final User user : feed.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
                break;
            }
        }

        mCompiledReply = JsoupUtils.parse(mReply);
    }

    @Override
    public String toString() {
        return getType().toString() + ':' + mUserId;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mReply = source.readString();
        mUserId = source.readString();
        mCompiledReply = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        mUser = source.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mReply);
        dest.writeString(mUserId);
        TextUtils.writeToParcel(mCompiledReply, dest, flags);
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
