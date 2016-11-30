package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Favorite implements DataObject, Parcelable {

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
        return o instanceof Favorite && mId.equals(((Favorite) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    public int getFavRank() {
        return mAttributes.mFavRank;
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
        dest.writeParcelable(mAttributes, flags);
        dest.writeParcelable(mDataType, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(final Parcel source) {
            final Favorite f = new Favorite();
            f.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            f.mDataType = source.readParcelable(DataType.class.getClassLoader());
            f.mLinks = source.readParcelable(Links.class.getClassLoader());
            f.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            f.mId = source.readString();
            return f;
        }

        @Override
        public Favorite[] newArray(final int size) {
            return new Favorite[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("favRank")
        private int mFavRank;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mFavRank);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mFavRank = source.readInt();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
