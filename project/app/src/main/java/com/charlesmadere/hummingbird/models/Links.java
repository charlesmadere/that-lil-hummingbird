package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class Links implements Parcelable {

    @Nullable
    @SerializedName("first")
    private String mFirst;

    @Nullable
    @SerializedName("last")
    private String mLast;

    @Nullable
    @SerializedName("next")
    private String mNext;

    @Nullable
    @SerializedName("related")
    private String mRelated;

    @Nullable
    @SerializedName("self")
    private String mSelf;


    @Nullable
    public String getFirst() {
        return mFirst;
    }

    @Nullable
    public String getLast() {
        return mLast;
    }

    @Nullable
    public String getNext() {
        return mNext;
    }

    @Nullable
    public String getRelated() {
        return mRelated;
    }

    @Nullable
    public String getSelf() {
        return mSelf;
    }

    public boolean hasFirst() {
        return !TextUtils.isEmpty(getFirst());
    }

    public boolean hasLast() {
        return !TextUtils.isEmpty(getLast());
    }

    public boolean hasNext() {
        return !TextUtils.isEmpty(getNext());
    }

    public boolean hasRelated() {
        return !TextUtils.isEmpty(getRelated());
    }

    public boolean hasSelf() {
        return !TextUtils.isEmpty(getSelf());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mFirst);
        dest.writeString(mLast);
        dest.writeString(mNext);
        dest.writeString(mRelated);
        dest.writeString(mSelf);
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(final Parcel source) {
            final Links l = new Links();
            l.mFirst = source.readString();
            l.mLast = source.readString();
            l.mNext = source.readString();
            l.mRelated = source.readString();
            l.mSelf = source.readString();
            return l;
        }

        @Override
        public Links[] newArray(final int size) {
            return new Links[size];
        }
    };

}
