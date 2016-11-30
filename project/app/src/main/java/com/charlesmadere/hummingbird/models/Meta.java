package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class Meta implements Parcelable {

    @Nullable
    @SerializedName("readonlyToken")
    private String mReadOnlyToken;


    @Nullable
    public String getReadOnlyToken() {
        return mReadOnlyToken;
    }

    public boolean hasReadOnlyToken() {
        return !TextUtils.isEmpty(mReadOnlyToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mReadOnlyToken);
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(final Parcel source) {
            final Meta m = new Meta();
            m.mReadOnlyToken = source.readString();
            return m;
        }

        @Override
        public Meta[] newArray(final int size) {
            return new Meta[size];
        }
    };

}
