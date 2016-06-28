package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class AuthInfo implements Parcelable {

    @SerializedName("password")
    private final String mPassword;

    @SerializedName("username")
    private final String mUsername;


    public AuthInfo(final String username, final String password) {
        if (TextUtils.isEmpty(username)) {
            throw new IllegalArgumentException("username can't be null / empty");
        } else if (TextUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password can't be null / empty");
        }

        mUsername = username;
        mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getUsername() {
        return mUsername;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mPassword);
        dest.writeString(mUsername);
    }

    public static final Creator<AuthInfo> CREATOR = new Creator<AuthInfo>() {
        @Override
        public AuthInfo createFromParcel(final Parcel source) {
            final String password = source.readString();
            final String username = source.readString();
            return new AuthInfo(username, password);
        }

        @Override
        public AuthInfo[] newArray(final int size) {
            return new AuthInfo[size];
        }
    };

}
