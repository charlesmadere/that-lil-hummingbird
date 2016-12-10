package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserRole implements DataObject, Parcelable {

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("links")
    private Links mLinks;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof UserRole && mId.equals(((UserRole) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Override
    public String getId() {
        return mId;
    }

    public Links getLinks() {
        return mLinks;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mDataType, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeString(mId);
    }

    public static final Creator<UserRole> CREATOR = new Creator<UserRole>() {
        @Override
        public UserRole createFromParcel(final Parcel source) {
            final UserRole ur = new UserRole();
            ur.mDataType = source.readParcelable(DataType.class.getClassLoader());
            ur.mLinks = source.readParcelable(Links.class.getClassLoader());
            ur.mId = source.readString();
            return ur;
        }

        @Override
        public UserRole[] newArray(final int size) {
            return new UserRole[size];
        }
    };

}
