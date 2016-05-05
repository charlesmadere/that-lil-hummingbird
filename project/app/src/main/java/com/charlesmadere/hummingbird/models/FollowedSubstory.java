package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

public class FollowedSubstory extends AbsSubstory implements Parcelable {

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    private AbsUser mUser;


    @Override
    public Type getType() {
        return Type.FOLLOWED;
    }

    public String getUserId() {
        return mUserId;
    }

    public AbsUser getUser() {
        return mUser;
    }

    public void setUser(final AbsUser user) {
        mUser = user;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mUserId = source.readString();
        mUser = ParcelableUtils.readAbsUserFromParcel(source);
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mUserId);
        ParcelableUtils.writeAbsUserToParcel(mUser, dest, flags);
    }

    public static final Creator<FollowedSubstory> CREATOR = new Creator<FollowedSubstory>() {
        @Override
        public FollowedSubstory createFromParcel(final Parcel source) {
            final FollowedSubstory fs = new FollowedSubstory();
            fs.readFromParcel(source);
            return fs;
        }

        @Override
        public FollowedSubstory[] newArray(final int size) {
            return new FollowedSubstory[size];
        }
    };

}
