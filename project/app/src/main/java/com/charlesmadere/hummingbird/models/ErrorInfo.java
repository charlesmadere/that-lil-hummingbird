package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorInfo implements Parcelable {

    @SerializedName("error")
    private String mError;


    public ErrorInfo() {

    }

    public ErrorInfo(final String error) {
        mError = error;
    }

    public String getError() {
        return mError;
    }

    @Override
    public String toString() {
        return getError();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mError);
    }

    public static final Creator<ErrorInfo> CREATOR = new Creator<ErrorInfo>() {
        @Override
        public ErrorInfo createFromParcel(final Parcel source) {
            final ErrorInfo ei = new ErrorInfo();
            ei.mError = source.readString();
            return ei;
        }

        @Override
        public ErrorInfo[] newArray(final int size) {
            return new ErrorInfo[size];
        }
    };

}
