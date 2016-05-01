package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentStory extends AbsStory implements Parcelable {

    @Nullable
    @SerializedName("recent_liker_ids")
    private ArrayList<String> mRecentLikerIds;

    @SerializedName("is_liked")
    private boolean mIsLiked;

    @SerializedName("group_id")
    private String mGroupId;

    @SerializedName("poster_id")
    private String mPosterId;


    public String getGroupId() {
        return mGroupId;
    }

    public String getPosterId() {
        return mPosterId;
    }

    @Nullable
    public ArrayList<String> getRecentLikerIds() {
        return mRecentLikerIds;
    }

    @Override
    public Type getType() {
        return Type.COMMENT;
    }

    public boolean hasRecentLikerIds() {
        return mRecentLikerIds != null && !mRecentLikerIds.isEmpty();
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mRecentLikerIds = source.createStringArrayList();
        mIsLiked = source.readInt() != 0;
        mGroupId = source.readString();
        mPosterId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(mRecentLikerIds);
        dest.writeInt(mIsLiked ? 1 : 0);
        dest.writeString(mGroupId);
        dest.writeString(mPosterId);
    }

    public static final Creator<CommentStory> CREATOR = new Creator<CommentStory>() {
        @Override
        public CommentStory createFromParcel(final Parcel source) {
            final CommentStory cs = new CommentStory();
            cs.readFromParcel(source);
            return cs;
        }

        @Override
        public CommentStory[] newArray(final int size) {
            return new CommentStory[size];
        }
    };

}
