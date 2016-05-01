package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public abstract class AbsStory implements Parcelable {

    @Nullable
    @SerializedName("substory_ids")
    private ArrayList<String> mSubstoryIds;

    @SerializedName("adult")
    private boolean mAdult;

    @SerializedName("substory_count")
    private int mSubstoryCount;

    @SerializedName("total_votes")
    private int mTotalVotes;

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("id")
    private String mId;

    @SerializedName("user_id")
    private String mUserId;


    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    public String getId() {
        return mId;
    }

    public int getSubstoryCount() {
        return mSubstoryCount;
    }

    @Nullable
    public ArrayList<String> getSubstoryIds() {
        return mSubstoryIds;
    }

    public int getTotalVotes() {
        return mTotalVotes;
    }

    public abstract Type getType();

    public String getUserId() {
        return mUserId;
    }

    public boolean hasSubstoryIds() {
        return mSubstoryIds != null && !mSubstoryIds.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mSubstoryIds = source.createStringArrayList();
        mAdult = source.readInt() != 0;
        mSubstoryCount = source.readInt();
        mTotalVotes = source.readInt();
        mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
        mId = source.readString();
        mUserId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeStringList(mSubstoryIds);
        dest.writeInt(mAdult ? 1 : 0);
        dest.writeInt(mSubstoryCount);
        dest.writeInt(mTotalVotes);
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeString(mId);
        dest.writeString(mUserId);
    }


    public enum Type implements Parcelable {
        @SerializedName("comment")
        COMMENT,

        @SerializedName("media_story")
        MEDIA_STORY;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Type> CREATOR = new Creator<Type>() {
            @Override
            public Type createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public Type[] newArray(final int size) {
                return new Type[size];
            }
        };
    }

}
