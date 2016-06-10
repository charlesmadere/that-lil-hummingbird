package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CommentPost implements Parcelable {

    @SerializedName("reply")
    private final String mReply;

    @SerializedName("story_id")
    private final String mStoryId;

    @SerializedName("type")
    private final String mType;

    @SerializedName("user_id")
    private final String mUserId;


    public CommentPost(final String reply, final String storyId) {
        if (TextUtils.isEmpty(reply)) {
            throw new IllegalArgumentException("reply can't be null / empty");
        } else if (TextUtils.isEmpty(storyId)) {
            throw new IllegalArgumentException("storyId can't be null / empty");
        }

        mReply = reply;
        mStoryId = storyId;
        mType = "reply";
        mUserId = Preferences.Account.Username.get();
    }

    private CommentPost(final Parcel source) {
        mReply = source.readString();
        mStoryId = source.readString();
        mType = source.readString();
        mUserId = source.readString();
    }

    public String getReply() {
        return mReply;
    }

    public String getStoryId() {
        return mStoryId;
    }

    public String getType() {
        return mType;
    }

    public String getUserId() {
        return mUserId;
    }

    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        json.add("substory", GsonUtils.getGson().toJsonTree(this));

        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mReply);
        dest.writeString(mStoryId);
        dest.writeString(mType);
        dest.writeString(mUserId);
    }

    public static final Creator<CommentPost> CREATOR = new Creator<CommentPost>() {
        @Override
        public CommentPost createFromParcel(final Parcel source) {
            return new CommentPost(source);
        }

        @Override
        public CommentPost[] newArray(final int size) {
            return new CommentPost[size];
        }
    };

}
