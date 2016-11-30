package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Person implements DataObject, Parcelable {

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
        return o instanceof Person && mId.equals(((Person) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Nullable
    public Image getImage() {
        return mAttributes.mImage;
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

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(final Parcel source) {
            final Person p = new Person();
            p.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            p.mDataType = source.readParcelable(DataType.class.getClassLoader());
            p.mLinks = source.readParcelable(Links.class.getClassLoader());
            p.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            p.mId = source.readString();
            return p;
        }

        @Override
        public Person[] newArray(final int size) {
            return new Person[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("image")
        private Image mImage;

        @SerializedName("name")
        private String mName;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mImage, flags);
            dest.writeString(mName);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mImage = source.readParcelable(Image.class.getClassLoader());
                a.mName = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
