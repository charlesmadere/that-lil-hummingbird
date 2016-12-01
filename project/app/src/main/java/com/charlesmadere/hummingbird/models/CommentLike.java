package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CommentLike implements DataObject, Parcelable {

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
        return o instanceof CommentLike && mId.equals(((CommentLike) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    public Links getLinks() {
        return mLinks;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    @Override
    public String getId() {
        return mId;
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

    public static final Creator<CommentLike> CREATOR = new Creator<CommentLike>() {
        @Override
        public CommentLike createFromParcel(final Parcel source) {
            final CommentLike cl = new CommentLike();
            cl.mDataType = source.readParcelable(DataType.class.getClassLoader());
            cl.mLinks = source.readParcelable(Links.class.getClassLoader());
            cl.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            cl.mId = source.readString();
            return cl;
        }

        @Override
        public CommentLike[] newArray(final int size) {
            return new CommentLike[size];
        }
    };

}
