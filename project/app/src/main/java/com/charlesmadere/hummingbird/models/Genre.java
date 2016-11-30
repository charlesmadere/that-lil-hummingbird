package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Genre implements DataObject, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("links")
    private Links mLinks;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof Genre && mId.equals(((Genre) o).getId());
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
        dest.writeString(mId);
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(final Parcel source) {
            final Genre f = new Genre();
            f.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            f.mDataType = source.readParcelable(DataType.class.getClassLoader());
            f.mLinks = source.readParcelable(Links.class.getClassLoader());
            f.mId = source.readString();
            return f;
        }

        @Override
        public Genre[] newArray(final int size) {
            return new Genre[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("description")
        private String mDescription;

        @SerializedName("name")
        private String mName;

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
