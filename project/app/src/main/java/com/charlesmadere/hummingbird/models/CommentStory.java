package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentStory extends AbsStory implements Parcelable {

    @Nullable
    @SerializedName("recent_liker_ids")
    private ArrayList<String> mRecentLikerIds;

    @SerializedName("is_liked")
    private boolean mIsLiked;

    @SerializedName("comment")
    private String mComment;

    @Nullable
    @SerializedName("group_id")
    private String mGroupId;

    @SerializedName("poster_id")
    private String mPosterId;

    // hydrated fields
    private AbsUser mPoster;

    @Nullable
    private Group mGroup;


    public String getComment() {
        return mComment;
    }

    @Nullable
    public Group getGroup() {
        return mGroup;
    }

    @Nullable
    public String getGroupId() {
        return mGroupId;
    }

    public AbsUser getPoster() {
        return mPoster;
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

    public boolean hasGroupId() {
        return !TextUtils.isEmpty(mGroupId);
    }

    public boolean hasRecentLikerIds() {
        return mRecentLikerIds != null && !mRecentLikerIds.isEmpty();
    }

    @Override
    public void hydrate(final Feed feed) {
        super.hydrate(feed);

        for (final AbsUser user : feed.getUsers()) {
            if (mPosterId.equalsIgnoreCase(user.getId())) {
                mPoster = user;
                break;
            }
        }

        if (hasGroupId()) {
            for (final Group group : feed.getGroups()) {
                if (mGroupId.equalsIgnoreCase(group.getId())) {
                    mGroup = group;
                    break;
                }
            }
        }
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public void setLiked(final boolean liked) {
        mIsLiked = liked;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mRecentLikerIds = source.createStringArrayList();
        mIsLiked = source.readInt() != 0;
        mComment = source.readString();
        mGroupId = source.readString();
        mPosterId = source.readString();
        mPoster = ParcelableUtils.readAbsUserFromParcel(source);
        mGroup = source.readParcelable(Group.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(mRecentLikerIds);
        dest.writeInt(mIsLiked ? 1 : 0);
        dest.writeString(mComment);
        dest.writeString(mGroupId);
        dest.writeString(mPosterId);
        ParcelableUtils.writeAbsUserToParcel(mPoster, dest, flags);
        dest.writeParcelable(mGroup, flags);
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
