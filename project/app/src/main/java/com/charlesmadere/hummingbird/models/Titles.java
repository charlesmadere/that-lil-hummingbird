package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class Titles implements Parcelable {

    @Nullable
    @SerializedName("en")
    private String mEn;

    @Nullable
    @SerializedName("en_jp")
    private String mEnJp;

    @Nullable
    @SerializedName("ja_jp")
    private String mJaJp;


    @Nullable
    public String getEn() {
        return mEn;
    }

    @Nullable
    public String getEnJp() {
        return mEnJp;
    }

    @Nullable
    public String getJaJp() {
        return mJaJp;
    }

    public boolean hasEn() {
        return !TextUtils.isEmpty(mEn);
    }

    public boolean hasEnJp() {
        return !TextUtils.isEmpty(mEnJp);
    }

    public boolean hasJaJp() {
        return !TextUtils.isEmpty(mJaJp);
    }

    @Override
    public String toString() {
        if (hasEn()) {
            return getEn();
        } else if (hasEnJp()) {
            return getEnJp();
        } else {
            return getJaJp();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mEn);
        dest.writeString(mEnJp);
        dest.writeString(mJaJp);
    }

    public static final Creator<Titles> CREATOR = new Creator<Titles>() {
        @Override
        public Titles createFromParcel(final Parcel source) {
            final Titles t = new Titles();
            t.mEn = source.readString();
            t.mEnJp = source.readString();
            t.mJaJp = source.readString();
            return t;
        }

        @Override
        public Titles[] newArray(final int size) {
            return new Titles[size];
        }
    };

}
