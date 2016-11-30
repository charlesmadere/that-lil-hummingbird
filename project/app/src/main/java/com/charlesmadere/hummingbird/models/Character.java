package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Character implements DataObject, Parcelable {

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
        return o instanceof Character && mId.equals(((Character) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Nullable
    public String getDescription() {
        return mAttributes.mDescription;
    }

    @Override
    public String getId() {
        return mId;
    }

    public Links getLinks() {
        return mLinks;
    }

    public String getName() {
        return mAttributes.mName;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    @Nullable
    public String getSlug() {
        return mAttributes.mSlug;
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

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(final Parcel source) {
            final Character c = new Character();
            c.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            c.mDataType = source.readParcelable(DataType.class.getClassLoader());
            c.mLinks = source.readParcelable(Links.class.getClassLoader());
            c.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            c.mId = source.readString();
            return c;
        }

        @Override
        public Character[] newArray(final int size) {
            return new Character[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("description")
        private String mDescription;

        @SerializedName("name")
        private String mName;

        @Nullable
        @SerializedName("slug")
        private String mSlug;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mDescription);
            dest.writeString(mName);
            dest.writeString(mSlug);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mDescription = source.readString();
                a.mName = source.readString();
                a.mSlug = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
