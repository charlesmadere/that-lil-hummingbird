package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public class GroupMember implements Parcelable {

    @SerializedName("pending")
    private boolean mPending;

    @SerializedName("rank")
    private Rank mRank;

    @SerializedName("group_id")
    private String mGroupId;

    @SerializedName("id")
    private String mId;

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    private User mUser;


    @Override
    public boolean equals(final Object o) {
        return o instanceof GroupMember && mId.equalsIgnoreCase(((GroupMember) o).getId());
    }

    public String getGroupId() {
        return mGroupId;
    }

    public String getId() {
        return mId;
    }

    public Rank getRank() {
        return mRank;
    }

    public User getUser() {
        return mUser;
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public void hydrate(final Feed feed) {
        for (final User user : feed.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
                break;
            }
        }
    }

    public boolean isPending() {
        return mPending;
    }

    public boolean isRankAdmin() {
        return mRank == Rank.ADMIN;
    }

    public boolean isRankPleb() {
        return mRank == Rank.PLEB;
    }

    @Override
    public String toString() {
        return mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mPending ? 1 : 0);
        dest.writeParcelable(mRank, flags);
        dest.writeString(mGroupId);
        dest.writeString(mId);
        dest.writeString(mUserId);
        dest.writeParcelable(mUser, flags);
    }

    public static final Creator<GroupMember> CREATOR = new Creator<GroupMember>() {
        @Override
        public GroupMember createFromParcel(final Parcel source) {
            final GroupMember gm = new GroupMember();
            gm.mPending = source.readInt() != 0;
            gm.mRank = source.readParcelable(Rank.class.getClassLoader());
            gm.mGroupId = source.readString();
            gm.mId = source.readString();
            gm.mUserId = source.readString();
            gm.mUser = source.readParcelable(User.class.getClassLoader());
            return gm;
        }

        @Override
        public GroupMember[] newArray(final int size) {
            return new GroupMember[size];
        }
    };


    public enum Rank implements Parcelable {
        @SerializedName("admin")
        ADMIN(R.string.admin),

        @SerializedName("pleb")
        PLEB(R.string.member);

        @StringRes
        private final int mTextResId;


        Rank(@StringRes final int textResId) {
            mTextResId = textResId;
        }

        @StringRes
        public int getTextResId() {
            return mTextResId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Rank> CREATOR = new Creator<Rank>() {
            @Override
            public Rank createFromParcel(final Parcel source) {
                return values()[source.readInt()];
            }

            @Override
            public Rank[] newArray(final int size) {
                return new Rank[size];
            }
        };
    }

}
