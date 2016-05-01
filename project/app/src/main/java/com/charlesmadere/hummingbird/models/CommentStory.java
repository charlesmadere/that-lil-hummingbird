package com.charlesmadere.hummingbird.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class CommentStory extends AbsStory {

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

    @Override
    public Type getType() {
        return Type.COMMENT;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mIsLiked = source.readInt() != 0;
        mGroupId = source.readString();
        mPosterId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
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
