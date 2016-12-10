package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Role implements DataObject, Parcelable {

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
        return o instanceof Role && mId.equals(((Role) o).getId());
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

    public String getName() {
        return mAttributes.mName;
    }

    @Nullable
    public String getResourceId() {
        return mAttributes.mResourceId;
    }

    @Nullable
    public String getResourceType() {
        return mAttributes.mResourceType;
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

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(final Parcel source) {
            final Role r = new Role();
            r.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            r.mDataType = source.readParcelable(DataType.class.getClassLoader());
            r.mLinks = source.readParcelable(Links.class.getClassLoader());
            r.mId = source.readString();
            return r;
        }

        @Override
        public Role[] newArray(final int size) {
            return new Role[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("name")
        private String mName;

        @Nullable
        @SerializedName("resourceId")
        private String mResourceId;

        @Nullable
        @SerializedName("resourceType")
        private String mResourceType;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mName);
            dest.writeString(mResourceId);
            dest.writeString(mResourceType);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mName = source.readString();
                a.mResourceId = source.readString();
                a.mResourceType = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
