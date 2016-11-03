package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.google.gson.annotations.SerializedName;

public class Liker implements Parcelable {

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("username")
    private String mUsername;

    // not included in json
    private String[] mAvatars;


    @Override
    public boolean equals(final Object o) {
        return o instanceof Liker && mUsername.equalsIgnoreCase(((Liker) o).getUsername());
    }

    public String getAvatar() {
        return mAvatar;
    }

    @Nullable
    public String[] getAvatars() {
        if (mAvatars == null) {
            mAvatars = MiscUtils.getUserAvatars(mAvatar);
        }

        return mAvatars;
    }

    public String getUsername() {
        return mUsername;
    }

    @Override
    public int hashCode() {
        return mUsername.hashCode();
    }

    @Override
    public String toString() {
        return getUsername();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mAvatar);
        dest.writeString(mUsername);
        dest.writeStringArray(mAvatars);
    }

    public static final Creator<Liker> CREATOR = new Creator<Liker>() {
        @Override
        public Liker createFromParcel(final Parcel source) {
            final Liker l = new Liker();
            l.mAvatar = source.readString();
            l.mUsername = source.readString();
            l.mAvatars = source.createStringArray();
            return l;
        }

        @Override
        public Liker[] newArray(final int size) {
            return new Liker[size];
        }
    };

}
