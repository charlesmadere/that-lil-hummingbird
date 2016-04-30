package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GroupMember implements Parcelable {

    @SerializedName("pending")
    private boolean mPending;

    @SerializedName("group_id")
    private String mGroupId;

    @SerializedName("id")
    private String mId;

    @SerializedName("rank")
    private String mRank;

    @SerializedName("user_id")
    private String mUserId;


    public String getGroupId() {
        return mGroupId;
    }

    public String getId() {
        return mId;
    }

    public String getRank() {
        return mRank;
    }

    public String getUserId() {
        return mUserId;
    }

    public boolean isPending() {
        return mPending;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mPending ? 1 : 0);
        dest.writeString(mGroupId);
        dest.writeString(mId);
        dest.writeString(mRank);
        dest.writeString(mUserId);
    }

    public static final Creator<GroupMember> CREATOR = new Creator<GroupMember>() {
        @Override
        public GroupMember createFromParcel(final Parcel source) {
            final GroupMember gm = new GroupMember();
            gm.mPending = source.readInt() != 0;
            gm.mGroupId = source.readString();
            gm.mId = source.readString();
            gm.mRank = source.readString();
            gm.mUserId = source.readString();
            return gm;
        }

        @Override
        public GroupMember[] newArray(final int size) {
            return new GroupMember[size];
        }
    };

}
