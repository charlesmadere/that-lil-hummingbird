package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Group implements Parcelable {

    @SerializedName("member_count")
    private int mMemberCount;

    @SerializedName("about")
    private String mAbout;

    @SerializedName("about_formatted")
    private String mAboutFormatted;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

    @SerializedName("bio")
    private String mBio;

    @SerializedName("cover_image_url")
    private String mCoverImageUrl;

    @SerializedName("current_member_id")
    private String mCurrentMemberId;

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    // hydrated fields
    @Nullable
    private ArrayList<GroupMember> mGroupMembers;


    public String getAbout() {
        return mAbout;
    }

    public String getAboutFormatted() {
        return mAboutFormatted;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getBio() {
        return mBio;
    }

    public String getCoverImageUrl() {
        return mCoverImageUrl;
    }

    public String getCurrentMemberId() {
        return mCurrentMemberId;
    }

    @Nullable
    public ArrayList<GroupMember> getGroupMembers() {
        return mGroupMembers;
    }

    public String getId() {
        return mId;
    }

    public int getMemberCount() {
        return mMemberCount;
    }

    public String getName() {
        return mName;
    }

    public boolean hasGroupMembers() {
        return mGroupMembers != null && !mGroupMembers.isEmpty();
    }

    public void hydrate(final Feed feed) {
        mGroupMembers = new ArrayList<>();

        for (final GroupMember groupMember : feed.getGroupMembers()) {
            if (mId.equalsIgnoreCase(groupMember.getGroupId())) {
                mGroupMembers.add(groupMember);
            }
        }

        mGroupMembers.trimToSize();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mMemberCount);
        dest.writeString(mAbout);
        dest.writeString(mAboutFormatted);
        dest.writeString(mAvatarUrl);
        dest.writeString(mBio);
        dest.writeString(mCoverImageUrl);
        dest.writeString(mCurrentMemberId);
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeTypedList(mGroupMembers);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(final Parcel source) {
            final Group g = new Group();
            g.mMemberCount = source.readInt();
            g.mAbout = source.readString();
            g.mAboutFormatted = source.readString();
            g.mAvatarUrl = source.readString();
            g.mBio = source.readString();
            g.mCoverImageUrl = source.readString();
            g.mCurrentMemberId = source.readString();
            g.mId = source.readString();
            g.mName = source.readString();
            g.mGroupMembers = source.createTypedArrayList(GroupMember.CREATOR);
            return g;
        }

        @Override
        public Group[] newArray(final int size) {
            return new Group[size];
        }
    };

}
