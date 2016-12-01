package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PostLike implements DataObject, Parcelable {

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
        return o instanceof PostLike && mId.equals(((PostLike) o).getId());
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

    public static final Creator<PostLike> CREATOR = new Creator<PostLike>() {
        @Override
        public PostLike createFromParcel(final Parcel source) {
            final PostLike pl = new PostLike();
            pl.mDataType = source.readParcelable(DataType.class.getClassLoader());
            pl.mLinks = source.readParcelable(Links.class.getClassLoader());
            pl.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            pl.mId = source.readString();
            return pl;
        }

        @Override
        public PostLike[] newArray(final int size) {
            return new PostLike[size];
        }
    };

}
