package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupDigest implements Parcelable {

    @Nullable
    @SerializedName("group_members")
    private ArrayList<GroupMember> mGroupMembers;

    @SerializedName("group")
    private Group mGroup;


    public Group getGroup() {
        return mGroup;
    }

    @Nullable
    public ArrayList<GroupMember> getGroupMembers() {
        return mGroupMembers;
    }

    public String getId() {
        return mGroup.getId();
    }

    public String getName() {
        return mGroup.getName();
    }

    public boolean hasGroupMembers() {
        return mGroupMembers != null && !mGroupMembers.isEmpty();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mGroupMembers);
        dest.writeParcelable(mGroup, flags);
    }

    public static final Creator<GroupDigest> CREATOR = new Creator<GroupDigest>() {
        @Override
        public GroupDigest createFromParcel(final Parcel source) {
            final GroupDigest gd = new GroupDigest();
            gd.mGroupMembers = source.createTypedArrayList(GroupMember.CREATOR);
            gd.mGroup = source.readParcelable(Group.class.getClassLoader());
            return gd;
        }

        @Override
        public GroupDigest[] newArray(final int size) {
            return new GroupDigest[size];
        }
    };

}
