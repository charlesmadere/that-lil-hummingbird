package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FranchiseV3 implements DataObject, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

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
        return o instanceof FranchiseV3 && mId.equals(((FranchiseV3) o).getId());
    }

    public String getCanonicalTitle() {
        return mAttributes.mCanonicalTitle;
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

    public Titles getTitles() {
        return mAttributes.mTitles;
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
        dest.writeParcelable(mAttributes, flags);
        dest.writeParcelable(mDataType, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<FranchiseV3> CREATOR = new Creator<FranchiseV3>() {
        @Override
        public FranchiseV3 createFromParcel(final Parcel source) {
            final FranchiseV3 f = new FranchiseV3();
            f.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            f.mDataType = source.readParcelable(DataType.class.getClassLoader());
            f.mLinks = source.readParcelable(Links.class.getClassLoader());
            f.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            f.mId = source.readString();
            return f;
        }

        @Override
        public FranchiseV3[] newArray(final int size) {
            return new FranchiseV3[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("canonicalTitle")
        private String mCanonicalTitle;

        @SerializedName("titles")
        private Titles mTitles;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mCanonicalTitle);
            dest.writeParcelable(mTitles, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mCanonicalTitle = source.readString();
                a.mTitles = source.readParcelable(Titles.class.getClassLoader());
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
