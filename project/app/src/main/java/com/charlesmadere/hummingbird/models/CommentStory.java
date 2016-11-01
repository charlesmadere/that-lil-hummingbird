package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;

public class CommentStory extends AbsStory implements Parcelable {

    @SerializedName("adult")
    private boolean mAdult;

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
    private CharSequence mCompiledComment;
    private Group mGroup;
    private User mPoster;

    // other
    private boolean mAdultBypassed;


    public CharSequence getComment() {
        return mCompiledComment;
    }

    public Group getGroup() {
        return mGroup;
    }

    @Nullable
    public String getGroupId() {
        return mGroupId;
    }

    public JsonObject getLikeJson() {
        final JsonObject story = new JsonObject();
        story.addProperty("id", getId());
        story.addProperty("is_liked", mIsLiked);

        final JsonObject json = new JsonObject();
        json.add("story", story);

        return json;
    }

    public User getPoster() {
        return mPoster;
    }

    public String getPosterId() {
        return mPosterId;
    }

    @Override
    public Type getType() {
        return Type.COMMENT;
    }

    public boolean hasGroupId() {
        return !TextUtils.isEmpty(mGroupId);
    }

    @Override
    @WorkerThread
    public void hydrate(final Feed feed) {
        super.hydrate(feed);

        // noinspection ConstantConditions
        for (final User user : feed.getUsers()) {
            if (mPosterId.equalsIgnoreCase(user.getId())) {
                mPoster = user;
                break;
            }
        }

        if (hasGroupId()) {
            // noinspection ConstantConditions
            for (final Group group : feed.getGroups()) {
                // noinspection ConstantConditions
                if (mGroupId.equalsIgnoreCase(group.getId())) {
                    mGroup = group;
                    break;
                }
            }
        }

        mCompiledComment = JsoupUtils.parse(mComment);

        if (hasSubstoryIds()) {
            Collections.sort(getSubstories(), AbsSubstory.CHRONOLOGICAL_ORDER);
        }
    }

    public boolean isAdult() {
        return mAdult;
    }

    public boolean isAdultBypassed() {
        return mAdultBypassed;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public boolean isPosterAndUserIdentical() {
        return mPosterId.equalsIgnoreCase(getUserId());
    }

    public void setAdultBypassed(final boolean adultBypassed) {
        mAdultBypassed = adultBypassed;
    }

    public void setLiked(final boolean liked) {
        if (liked == mIsLiked) {
            return;
        }

        mIsLiked = liked;

        if (mIsLiked) {
            setTotalVotes(getTotalVotes() + 1);
        } else {
            setTotalVotes(getTotalVotes() - 1);
        }
    }

    public void toggleLiked() {
        setLiked(!mIsLiked);
    }

    @Override
    public String toString() {
        return getType() + ":" + mPosterId;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mAdult = source.readInt() != 0;
        mIsLiked = source.readInt() != 0;
        mComment = source.readString();
        mGroupId = source.readString();
        mPosterId = source.readString();
        mCompiledComment = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        mPoster = source.readParcelable(User.class.getClassLoader());
        mGroup = source.readParcelable(Group.class.getClassLoader());
        mAdultBypassed = source.readInt() != 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mAdult ? 1 : 0);
        dest.writeInt(mIsLiked ? 1 : 0);
        dest.writeString(mComment);
        dest.writeString(mGroupId);
        dest.writeString(mPosterId);
        TextUtils.writeToParcel(mCompiledComment, dest, flags);
        dest.writeParcelable(mPoster, flags);
        dest.writeParcelable(mGroup, flags);
        dest.writeInt(mAdultBypassed ? 1 : 0);
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
