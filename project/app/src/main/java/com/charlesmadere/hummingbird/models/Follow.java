package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Follow implements DataObject, Parcelable {

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("links")
    private Links mLinks;

    @SerializedName("relationships")
    private Relationships mRelationships;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof Follow && mId.equals(((Follow) o).getId());
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

    public Relationships getRelationships() {
        return mRelationships;
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
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<Follow> CREATOR = new Creator<Follow>() {
        @Override
        public Follow createFromParcel(final Parcel source) {
            final Follow f = new Follow();
            f.mDataType = source.readParcelable(DataType.class.getClassLoader());
            f.mLinks = source.readParcelable(Links.class.getClassLoader());
            f.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            f.mId = source.readString();
            return f;
        }

        @Override
        public Follow[] newArray(final int size) {
            return new Follow[size];
        }
    };

}
