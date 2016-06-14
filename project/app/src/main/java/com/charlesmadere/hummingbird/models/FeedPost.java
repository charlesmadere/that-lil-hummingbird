package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class FeedPost implements Parcelable {

    @SerializedName("adult")
    private final boolean mAdult;

    @SerializedName("comment")
    private final String mComment;

    @SerializedName("poster_id")
    private final String mPosterId;

    @SerializedName("type")
    private final String mType;

    @SerializedName("user_id")
    private final String mUserId;


    public FeedPost(final boolean adult, final String comment, final String userId) {
        if (TextUtils.isEmpty(comment) || TextUtils.getTrimmedLength(comment) == 0) {
            throw new IllegalArgumentException("comment can't be null / empty / whitespace");
        } else if (TextUtils.isEmpty(userId) || TextUtils.getTrimmedLength(userId) == 0) {
            throw new IllegalArgumentException("userId can't be null / empty / whitespace");
        }

        mAdult = adult;
        mComment = comment;
        mPosterId = Preferences.Account.Username.get();
        mType = "comment";
        mUserId = userId;
    }

    private FeedPost(final Parcel source) {
        mAdult = source.readInt() != 0;
        mComment = source.readString();
        mPosterId = source.readString();
        mType = source.readString();
        mUserId = source.readString();
    }

    public boolean isAdult() {
        return mAdult;
    }

    public String getComment() {
        return mComment;
    }

    public String getPosterId() {
        return mPosterId;
    }

    public String getType() {
        return mType;
    }

    public String getUserId() {
        return mUserId;
    }

    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        json.add("story", GsonUtils.getGson().toJsonTree(this));

        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mAdult ? 1 : 0);
        dest.writeString(mComment);
        dest.writeString(mPosterId);
        dest.writeString(mType);
        dest.writeString(mUserId);
    }

    public static final Creator<FeedPost> CREATOR = new Creator<FeedPost>() {
        @Override
        public FeedPost createFromParcel(final Parcel source) {
            return new FeedPost(source);
        }

        @Override
        public FeedPost[] newArray(final int size) {
            return new FeedPost[size];
        }
    };

}
